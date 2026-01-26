// admin.js - PAINEL COMPLETO ADMIN - CRUD Todas as entidades
class AdminApp {
    constructor() {
        this.currentEntity = null;
        this.currentId = null;
        this.init();
    }

    init() {
        this.loadAllData();
        this.setupEventListeners();
        this.checkAdminAccess();
    }

    async loadAllData() {
        try {
            const [usuarios, clientes, produtos, pedidos] = await Promise.all([
                apiGet('/api/usuarios'),
                apiGet('/api/clientes'),
                apiGet('/api/produtos'),
                apiGet('/api/pedidos')
            ]);
            
            this.renderTable('usuarios', usuarios);
            this.renderTable('clientes', clientes);
            this.renderTable('produtos', produtos);
            this.renderTable('pedidos', pedidos);
        } catch (error) {
            console.error('Erro ao carregar dados:', error);
            showError('Erro ao carregar dados do sistema.');
        }
    }

    setupEventListeners() {
        // CRUD Form
        document.getElementById('crud-form').addEventListener('submit', (e) => this.handleSubmit(e));
        
        // Modal events
        document.getElementById('crudModal').addEventListener('hidden.bs.modal', () => this.resetModal());
    }

    checkAdminAccess() {
        if (!AuthManager.isAdmin()) {
            showError('Acesso negado. Apenas administradores.');
            setTimeout(() => window.location.href = 'index.html', 2000);
        }
    }

    openCrudModal(entity, id = null) {
        this.currentEntity = entity;
        this.currentId = id;
        
        const title = id ? `Editar ${this.getEntityName(entity)}` : `Novo ${this.getEntityName(entity)}`;
        document.getElementById('modal-title').textContent = title;
        
        this.generateFormFields(entity, id);
    }

    generateFormFields(entity, id) {
        const modalBody = document.getElementById('modal-body');
        modalBody.innerHTML = '';

        const fields = this.getEntityFields(entity);
        
        fields.forEach(field => {
            const div = document.createElement('div');
            div.className = 'mb-3';
            div.innerHTML = `
                <label class="form-label">${field.label} ${field.required ? '*' : ''}</label>
                ${this.createFieldElement(field, entity)}
            `;
            modalBody.appendChild(div);
        });
    }

    createFieldElement(field, entity) {
        const fieldId = `${entity}-${field.name}`;
        
        switch(field.type) {
            case 'select':
                return `
                    <select class="form-select" id="${fieldId}" ${field.required ? 'required' : ''}>
                        ${field.options.map(opt => `<option value="${opt.value}">${opt.label}</option>`).join('')}
                    </select>
                `;
            case 'number':
                return `<input type="number" class="form-control" id="${fieldId}" ${field.required ? 'required' : ''} ${field.step ? `step="${field.step}"` : ''}>`;
            case 'email':
                return `<input type="email" class="form-control" id="${fieldId}" ${field.required ? 'required' : ''}>`;
            default:
                return `<input type="${field.type}" class="form-control" id="${fieldId}" ${field.required ? 'required' : ''} ${field.maxlength ? `maxlength="${field.maxlength}"` : ''}>`;
        }
    }

    getEntityFields(entity) {
        const fields = {
            usuarios: [
                { name: 'username', label: 'Username', type: 'text', required: true, maxlength: 50 },
                { name: 'password', label: 'Senha', type: 'password', required: true },
                { name: 'perfil', label: 'Perfil', type: 'select', required: true, 
                  options: [
                      {value: 'ADMIN', label: 'ADMIN'},
                      {value: 'CLIENTE', label: 'CLIENTE'},
                      {value: 'OUTROS', label: 'OUTROS'}
                  ]
                },
                { name: 'idcliente', label: 'Cliente Vinculado', type: 'select' }
            ],
            clientes: [
                { name: 'nome', label: 'Nome', type: 'text', required: true },
                { name: 'cpf', label: 'CPF', type: 'text', maxlength: 14 },
                { name: 'email', label: 'Email', type: 'email' }
            ],
            produtos: [
                { name: 'nome', label: 'Nome', type: 'text', required: true },
                { name: 'preco', label: 'Preço', type: 'number', required: true, step: '0.01' },
                { name: 'estoque', label: 'Estoque', type: 'number', required: true }
            ]
        };
        return fields[entity] || [];
    }

    async handleSubmit(e) {
        e.preventDefault();
        
        const data = this.collectFormData();
        const endpoint = this.currentId ? `/${this.currentId}` : '';
        
        try {
            if (this.currentId) {
                await apiPut(`/${this.currentEntity}${endpoint}`, data);
                showSuccess('Registro atualizado!');
            } else {
                await apiPost(`/${this.currentEntity}`, data);
                showSuccess('Registro criado!');
            }
            
            this.loadAllData();
            bootstrap.Modal.getInstance(document.getElementById('crudModal')).hide();
        } catch (error) {
            showError(error.message || 'Erro ao salvar.');
        }
    }

    collectFormData() {
        const data = {};
        const fields = this.getEntityFields(this.currentEntity);
        
        fields.forEach(field => {
            const element = document.getElementById(`${this.currentEntity}-${field.name}`);
            if (element && element.value) {
                data[field.name] = field.type === 'number' ? parseFloat(element.value) : element.value;
            }
        });
        
        return data;
    }

    async renderTable(entity, items) {
        const tbody = document.getElementById(`${entity}-table`);
        if (!tbody) return;

        tbody.innerHTML = items.length ? 
            items.map(item => this.createTableRow(entity, item)).join('') :
            `<tr><td colspan="${this.getTableCols(entity)}" class="text-center text-muted py-4">Nenhum ${this.getEntityName(entity, true)} encontrado</td></tr>`;

        this.setupTableActions(entity);
    }

    createTableRow(entity, item) {
        const cols = this.getTableColumns(entity);
        let row = `<tr>`;

        cols.forEach(col => {
            if (col === 'actions') {
                row += `<td>${this.createActionButtons(entity, item.id || item.idpedido || item.idcliente)}</td>`;
            } else {
                const value = item[col];
                row += col === 'perfil' ? 
                    `<td><span class="badge bg-${this.getBadgeClass(value)}">${value}</span></td>` :
                    `<td>${this.formatValue(col, value, item)}</td>`;
            }
        });
        
        row += `</tr>`;
        return row;
    }

    setupTableActions(entity) {
        document.querySelectorAll(`#${entity}-table .btn-edit`).forEach(btn => {
            btn.onclick = () => this.editItem(entity, btn.dataset.id);
        });
        
        document.querySelectorAll(`#${entity}-table .btn-delete`).forEach(btn => {
            btn.onclick = () => this.deleteItem(entity, btn.dataset.id);
        });
    }

    // HELPERS
    getEntityName(entity, plural = false) {
        const names = {
            usuarios: plural ? 'usuários' : 'usuário',
            clientes: plural ? 'clientes' : 'cliente',
            produtos: plural ? 'produtos' : 'produto'
        };
        return names[entity] || entity;
    }

    getBadgeClass(perfil) {
        return { ADMIN: 'danger', CLIENTE: 'primary', OUTROS: 'secondary' }[perfil] || 'secondary';
    }

    resetModal() {
        this.currentEntity = null;
        this.currentId = null;
        document.getElementById('crud-form').reset();
    }
}

// Inicializar
document.addEventListener('DOMContentLoaded', () => {
    window.adminApp = new AdminApp();
    document.getElementById('admin-nome').textContent = AuthManager.getCurrentUser().username;
});
