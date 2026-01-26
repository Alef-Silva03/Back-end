// Máscara para CPF
const aplicarMascaraCPF = (value) => {
    return value
        .replace(/\D/g, '') 
        .replace(/(\d{3})(\d)/, '$1.$2')
        .replace(/(\d{3})(\d)/, '$1.$2')
        .replace(/(\d{3})(\d{1,2})/, '$1-$2')
        .replace(/(-\d{2})\d+?$/, '$1');
};

// Máscara para CEP
const aplicarMascaraCEP = (value) => {
    return value
        .replace(/\D/g, '')
        .replace(/(\d{5})(\d)/, '$1-$2')
        .replace(/(-\d{3})\d+?$/, '$1');
};

// Ouvintes de eventos para os campos
document.addEventListener('DOMContentLoaded', () => {
    const cpfInput = document.querySelector('input[name="cpf"]');
    const cepInput = document.getElementById('cep');

    if (cpfInput) {
        cpfInput.addEventListener('input', (e) => {
            e.target.value = aplicarMascaraCPF(e.target.value);
        });
    }

    if (cepInput) {
        cepInput.addEventListener('input', (e) => {
            e.target.value = aplicarMascaraCEP(e.target.value);
        });
    }
});