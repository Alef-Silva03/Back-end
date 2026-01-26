// js/auth.js - 100% COMPATÍVEL com Bootstrap + seu HTML
class AuthManager {
    constructor() {
        console.log('🚀 AuthManager carregado');
    }

    async login(username, password) {
        console.log('🔄 LOGANDO:', username);
        
        try {
            // ✅ Spinner ativo
            document.getElementById('login-spinner').classList.remove('d-none');
            
            const response = await axios.post('/api/auth/login', {
                username: username.trim(),
                password: password
            });
            
            console.log('✅ LOGIN OK:', response.data);
            
            // ✅ Salva NO FORMATO que admin.html espera
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('user', JSON.stringify({
                perfil: response.data.perfil,
                username: username
            }));
            
            // ✅ REDIRECIONA IMEDIATAMENTE
            window.location.replace('admin.html');
            
        } catch (error) {
            console.error('❌ LOGIN FALHOU:', error.response?.data || error.message);
            const errorDiv = document.getElementById('login-error');
            errorDiv.textContent = error.response?.data?.message || 'Usuário ou senha inválidos!';
            errorDiv.classList.remove('d-none');
        } finally {
            document.getElementById('login-spinner').classList.add('d-none');
        }
    }

    async register(username, password, perfil) {
        console.log('🔄 CADASTRANDO:', username, perfil);
        
        try {
            document.getElementById('register-spinner').classList.remove('d-none');
            
            const isCliente = perfil === 'CLIENTE';
            const data = {
                username: username.trim(),
                password: password,
                perfil: perfil
            };
            
            // ✅ Campos cliente se necessário
            if (isCliente) {
                data.cliente = {
                    nome: document.getElementById('cliente_nome').value,
                    cpf: document.getElementById('cliente_cpf').value,
                    email: document.getElementById('cliente_email').value,
                    telefone: document.getElementById('cliente_telefone').value
                };
            }
            
            await axios.post('/api/auth/register', data);
            
            // ✅ Troca para login
            const loginTab = new bootstrap.Tab(document.getElementById('login-tab'));
            loginTab.show();
            
            // ✅ Toast sucesso
            showCustomToast('Cadastro realizado! Faça login.', 'success');
            
        } catch (error) {
            const errorDiv = document.getElementById('register-error');
            errorDiv.textContent = error.response?.data?.message || 'Erro no cadastro!';
            errorDiv.classList.remove('d-none');
        } finally {
            document.getElementById('register-spinner').classList.add('d-none');
        }
    }
}

// ✅ GLOBAL
window.Auth = new AuthManager();

// ✅ Event Listeners para seus forms
document.addEventListener('DOMContentLoaded', function() {
    console.log('✅ auth.js inicializado');
    
    // ✅ Login Form
    document.getElementById('login-form').addEventListener('submit', async function(e) {
        e.preventDefault();
        const username = document.getElementById('login-username').value;
        const password = document.getElementById('login-password').value;
        
        if (username && password) {
            await window.Auth.login(username, password);
        }
    });
    
    // ✅ Register Form
    document.getElementById('register-form').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const username = document.getElementById('reg-username').value;
        const password = document.getElementById('reg-password').value;
        const confirmPassword = document.getElementById('reg-confirm-password').value;
        const perfil = document.getElementById('reg-perfil').value;
        
        // ✅ Validações
        if (password !== confirmPassword) {
            showCustomToast('Senhas não coincidem!', 'error');
            return;
        }
        
        if (!username || !password || !perfil) {
            showCustomToast('Preencha todos os campos obrigatórios!', 'error');
            return;
        }
        
        await window.Auth.register(username, password, perfil);
    });
    
    // ✅ Toggle campos cliente
    document.getElementById('reg-perfil').addEventListener('change', function() {
        const clienteFields = document.getElementById('cliente-fields');
        clienteFields.style.display = this.value === 'CLIENTE' ? 'block' : 'none';
    });
    
    // ✅ Enter funciona
    document.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            const activeTab = document.querySelector('.tab-pane.active');
            if (activeTab.id === 'login-tab-pane') {
                document.getElementById('login-form').dispatchEvent(new Event('submit'));
            }
        }
    });
});

// ✅ Toast customizado para seu design
function showCustomToast(message, type = 'success') {
    const toast = document.createElement('div');
    toast.className = `alert alert-${type === 'success' ? 'success' : 'danger'} custom-toast position-fixed`;
    toast.innerHTML = `
        ${message}
        <button type="button" class="btn-close ms-2 float-end" data-bs-dismiss="alert"></button>
    `;
    document.body.appendChild(toast);
    
    // ✅ Bootstrap toast
    const bsToast = new bootstrap.Toast(toast);
    bsToast.show();
    
    setTimeout(() => toast.remove(), 5000);
}

console.log('✅ auth.js COMPLETO - simone/123456 (ADMIN)');

          