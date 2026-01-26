// --- FUNÇÕES DE MÁSCARA ---
const aplicarMascara = (id, maskFn) => {
    const input = document.getElementById(id);
    if (input) {
        input.addEventListener('input', e => {
            e.target.value = maskFn(e.target.value);
        });
    }
};

const maskCPF = v => v.replace(/\D/g, '').replace(/(\d{3})(\d)/, '$1.$2').replace(/(\d{3})(\d)/, '$1.$2').replace(/(\d{3})(\d{1,2})$/, '$1-$2').substring(0, 14);
const maskTel = v => v.replace(/\D/g, '').replace(/^(\d{2})(\d)/g, '($1) $2').replace(/(\d{5})(\d)/, '$1-$2').substring(0, 15);

// Aplicando as máscaras aos campos
aplicarMascara('cpf', maskCPF);
aplicarMascara('telefone', maskTel);

// --- SUBMISSÃO DO FORMULÁRIO ---
document.getElementById("cadastroForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    
    const form = event.target;
    const btn = document.getElementById("btnEnviar");
    const msgSucesso = document.getElementById("mensagem-sucesso");
    
    // Limpar mensagens de erro anteriores
    document.querySelectorAll('.text-danger').forEach(el => el.innerText = '');
    msgSucesso.classList.add('d-none');

    // Mapeamento de todos os campos para o JSON (Engenharia de DTO)
    const dados = {
        nome: document.getElementById("nome").value,
        email: document.getElementById("email").value,
        cpf: document.getElementById("cpf").value.replace(/\D/g, ''), // Envia apenas números
        telefone: document.getElementById("telefone").value,   
        numero: document.getElementById("apartamento").value,      
    };

    // Bloquear botão para evitar cliques duplos
    btn.disabled = true;
    btn.innerHTML = `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Processando...`;

    try {
        const response = await fetch('/api/usuarios', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dados)
        });

        if (response.status === 400) {
            const errosDoServidor = await response.json();
            // Mapeia os erros vindo do @Valid do Spring Boot
            Object.keys(errosDoServidor).forEach(campo => {
                const spanErro = document.getElementById(`err-${campo}`);
                if (spanErro) spanErro.innerText = errosDoServidor[campo];
            });
        } 
        else if (response.ok) {
            form.reset(); 
            msgSucesso.classList.remove('d-none'); 
            window.scrollTo(0, 0); // Sobe a página para mostrar o sucesso
            
            setTimeout(() => {
                msgSucesso.classList.add('d-none');
            }, 5000);
        } else {
            const erroMsg = await response.text();
            alert("Erro do Servidor: " + erroMsg);
        }
    } catch (error) {
        alert("Erro crítico ao conectar com a API Java. Verifique se o backend está rodando.");
    } finally {
        btn.disabled = false;
        btn.innerText = "Finalizar Cadastro";
    }
});