// api.js - Chamadas API centralizadas para sistema Vendas2
// Compatível com Spring Boot + tabela usuario (idusuario, username, password, perfil, idcliente)

const API_BASE_URL = 'http://localhost:8080/api'; // Ajuste para seu backend Spring Boot

// Configuração padrão do fetch com token JWT
const apiConfig = {
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
    }
};

// ========== GET - Buscar dados ==========
async function apiGet(endpoint) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: 'GET',
            headers: apiConfig.headers
        });
        
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || `HTTP ${response.status}: ${response.statusText}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('API GET Error:', error);
        throw error;
    }
}

// GET por ID específico
async function apiGetById(endpoint, id) {
    return apiGet(`${endpoint}/${id}`);
}

// ========== POST - Criar novo ==========
async function apiPost(endpoint, data) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: 'POST',
            headers: apiConfig.headers,
            body: JSON.stringify(data)
        });
        
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || `HTTP ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('API POST Error:', error);
        throw error;
    }
}

// ========== PUT - Atualizar ==========
async function apiPut(endpoint, data) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: 'PUT',
            headers: apiConfig.headers,
            body: JSON.stringify(data)
        });
        
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || `HTTP ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('API PUT Error:', error);
        throw error;
    }
}

// ========== DELETE - Excluir ==========
async function apiDelete(endpoint) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: 'DELETE',
            headers: apiConfig.headers
        });
        
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || `HTTP ${response.status}`);
        }
        
        return true;
    } catch (error) {
        console.error('API DELETE Error:', error);
        throw error;
    }
}

// ========== UTILITÁRIOS ==========

// Atualizar token JWT no header
function updateAuthToken(token) {
    apiConfig.headers.Authorization = `Bearer ${token}`;
    localStorage.setItem('token', token);
}

// Salvar token após login
function saveAuthToken(token) {
    localStorage.setItem('token', token);
    updateAuthToken(token);
}

// Verificar se token existe
function hasAuthToken() {
    return !!localStorage.getItem('token');
}

// Limpar autenticação
function clearAuthToken() {
    localStorage.removeItem('token');
    apiConfig.headers.Authorization = '';
}

// Verificar permissão ADMIN
async function checkAdminPermission() {
    try {
        const user = await apiGet('/auth/me');
        return user.perfil === 'ADMIN';
    } catch {
        return false;
    }
}

// ========== ENDPOINTS ESPECÍFICOS DO VENDAS2 ==========

// Usuários (compatível com sua tabela usuario)
async function getUsuarios() {
    return apiGet('/usuarios');
}

async function getUsuarioById(id) {
    return apiGet(`/usuarios/${id}`);
}

async function createUsuario(usuario) {
    return apiPost('/usuarios', usuario);
}

async function updateUsuario(id, usuario) {
    return apiPut(`/usuarios/${id}`, usuario);
}

async function deleteUsuario(id) {
    return apiDelete(`/usuarios/${id}`);
}

// Clientes
async function getClientes() {
    return apiGet('/clientes');
}

async function getClienteById(id) {
    return apiGet(`/clientes/${id}`);
}

// Produtos
async function getProdutos() {
    return apiGet('/produtos');
}

// Pedidos
async function getPedidos() {
    return apiGet('/pedidos');
}

// ========== FUNÇÕES GLOBAIS (disponíveis em window) ==========
window.apiGet = apiGet;
window.apiGetById = apiGetById;
window.apiPost = apiPost;
window.apiPut = apiPut;
window.apiDelete = apiDelete;
window.updateAuthToken = updateAuthToken;
window.saveAuthToken = saveAuthToken;
window.clearAuthToken = clearAuthToken;
window.checkAdminPermission = checkAdminPermission;

// Endpoints específicos
window.getUsuarios = getUsuarios;
window.getUsuarioById = getUsuarioById;
window.createUsuario = createUsuario;
window.updateUsuario = updateUsuario;
window.deleteUsuario = deleteUsuario;
window.getClientes = getClientes;
window.getProdutos = getProdutos;
window.getPedidos = getPedidos;

// ========== NOTIFICAÇÕES GLOBAIS ==========
window.showSuccess = (message) => {
    // Use seu sistema de notificação preferido (toastr, sweetalert, etc.)
    const toast = document.createElement('div');
    toast.className = 'alert alert-success position-fixed alert-dismissible fade show';
    toast.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    toast.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    document.body.appendChild(toast);
    
    setTimeout(() => {
        if (toast.parentNode) toast.remove();
    }, 5000);
};

window.showError = (message) => {
    const toast = document.createElement('div');
    toast.className = 'alert alert-danger position-fixed alert-dismissible fade show';
    toast.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    toast.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    document.body.appendChild(toast);
    
    setTimeout(() => {
        if (toast.parentNode) toast.remove();
    }, 7000);
};

console.log('api.js carregado - Sistema Vendas2 API Client');
