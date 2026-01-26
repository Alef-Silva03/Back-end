// app.js - Layout + Navegação global Sistema Vendas2

class AppLayout {
    constructor() {
        this.init();
    }

    init() {
        this.setupMobileMenu();
        this.setupTooltips();
        this.setupModals();
        this.handleResize();
        this.loadTheme();
    }

    setupMobileMenu() {
        // Mobile sidebar toggle
        const sidebarToggle = document.querySelector('.navbar-toggler');
        if (sidebarToggle) {
            sidebarToggle.addEventListener('click', () => {
                document.querySelector('.sidebar').classList.toggle('show');
            });
        }

        // Close sidebar on outside click (mobile)
        document.addEventListener('click', (e) => {
            const sidebar = document.querySelector('.sidebar');
            const toggle = document.querySelector('.navbar-toggler');
            
            if (window.innerWidth <= 768 && 
                !sidebar.contains(e.target) && 
                !toggle?.contains(e.target)) {
                sidebar?.classList.remove('show');
            }
        });
    }

    setupTooltips() {
        const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        tooltipTriggerList.map(function (tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl);
        });
    }

    setupModals() {
        // Auto focus no primeiro input do modal
        document.addEventListener('shown.bs.modal', (e) => {
            const firstInput = e.target.querySelector('input, select');
            if (firstInput) {
                firstInput.focus();
            }
        });
    }

    handleResize() {
        window.addEventListener('resize', () => {
            if (window.innerWidth > 768) {
                document.querySelector('.sidebar')?.classList.remove('show');
            }
        });
    }

    loadTheme() {
        // Dark mode toggle (futuro)
        const savedTheme = localStorage.getItem('theme') || 'light';
        document.body.setAttribute('data-theme', savedTheme);
    }

    showNotification(message, type = 'success') {
        const alertTypes = {
            success: 'alert-success',
            error: 'alert-danger',
            warning: 'alert-warning',
            info: 'alert-info'
        };

        const alert = document.createElement('div');
        alert.className = `alert ${alertTypes[type] || 'alert-info'} alert-dismissible fade show position-fixed`;
        alert.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
        alert.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        
        document.body.appendChild(alert);
        
        setTimeout(() => {
            if (alert.parentNode) {
                alert.remove();
            }
        }, 5000);
    }
}

// Inicializar app quando DOM carregar
document.addEventListener('DOMContentLoaded', () => {
    window.AppLayout = new AppLayout();
    
    // Scroll suave para âncoras
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });
});

// Polyfill para NodeList.forEach
if (window.NodeList && !NodeList.prototype.forEach) {
    NodeList.prototype.forEach = Array.prototype.forEach;
}

console.log('app.js carregado - Layout + Navegação');
