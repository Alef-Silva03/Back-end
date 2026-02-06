document.addEventListener("DOMContentLoaded", () => {

	// ===== DETECTAR QUAL PÁGINA ESTÁ ABERTA =====
	const loginForm = document.getElementById("loginForm");
	const cadastroForm = document.getElementById("cadastroForm");
	const dashboardPage = document.getElementById("dashboardPage");

	// ================= LOGIN =================
	if (loginForm) {
		loginForm.onsubmit = async (e) => {
			e.preventDefault();

			try {
				const payload = {
					email: document.getElementById("email").value,
					senha: document.getElementById("senha").value
				};

				const res = await fetch("/api/auth/login", {
					method: "POST",
					headers: { "Content-Type": "application/json" },
					body: JSON.stringify(payload)
				});

				if (!res.ok) throw new Error();

				const user = await res.json();

				localStorage.setItem("perfil", user.perfil);
				localStorage.setItem("nomeUsuario", user.nome);

				window.location.href = "dashboard.html";

			} catch {
				alert("Login inválido");
			}
		};
	}

	// ================= CADASTRO =================
	if (cadastroForm) {
		cadastroForm.onsubmit = async (e) => {
			e.preventDefault();

			try {
				const payload = {
					nome: document.getElementById("cadNome").value,
					email: document.getElementById("cadEmail").value,
					senha: document.getElementById("cadSenha").value,
					cpf: document.getElementById("cadCpf").value,
					telefone: document.getElementById("cadTelefone").value,
					apartamento: document.getElementById("cadApartamento").value,
					perfil: document.getElementById("cadPerfil").value
				};

				const res = await fetch("/api/auth/cadastro", {
					method: "POST",
					headers: { "Content-Type": "application/json" },
					body: JSON.stringify(payload)
				});

				const data = await res.json();

				alert(data.message || "Cadastro realizado");

				if (res.ok) cadastroForm.reset();

			} catch {
				alert("Erro ao cadastrar");
			}
		};
	}

	// ================= DASHBOARD =================
	if (dashboardPage) {

		const PERFIL = localStorage.getItem("perfil");
		const NOME = localStorage.getItem("nomeUsuario");

		if (!PERFIL) {
			window.location.href = "login.html";
			return;
		}

		configurarInterface(PERFIL, NOME);
		carregarDadosServidor();
	}

});


// ================= INTERFACE =================
function configurarInterface(PERFIL, NOME) {

	const userName = document.getElementById("userName");
	if (userName) userName.textContent = NOME;

	if (PERFIL === "ADMIN") {
		document.querySelectorAll(".admin-only")
			.forEach(el => el.style.display = "block");

		const perfilLabel = document.getElementById("perfilLabel");
		if (perfilLabel)
			perfilLabel.innerHTML = '<span class="admin-badge">ADMINISTRADOR</span>';

	} else {
		const banner = document.getElementById("moradorBanner");
		if (banner) banner.style.display = "block";

		const perfilLabel = document.getElementById("perfilLabel");
		if (perfilLabel)
			perfilLabel.innerHTML = '<span class="client-badge">CLIENTE</span>';
	}
}


// ================= DADOS DO SERVIDOR =================
async function carregarDadosServidor() {
	try {

		const res = await fetch("/api/dashboard/perfil");

		if (res.status === 401 || res.status === 403) {
			window.location.href = "login.html";
			return;
		}

		if (!res.ok) throw new Error();

		const user = await res.json();

		setText("infoNome", user.nome);
		setText("infoEmail", user.email);
		setText("infoTelefone", user.telefone);
		setText("infoApartamento", user.apartamento);
		setText("infoPerfil", user.perfil);
		setText("infoID", user.id);

	} catch {
		console.error("Erro ao carregar dados");
	}
}

function setText(id, value) {
	const el = document.getElementById(id);
	if (el) el.textContent = value || "-";
}


// ================= LOGOUT =================
async function logout() {
	if (confirm("Deseja encerrar sua sessão segura?")) {
		await fetch("/api/auth/logout", { method: "POST" });
		localStorage.clear();
		window.location.href = "login.html";
	}
}


// ================= TOGGLE PERFIL =================
function toggleCampos() {
	console.log("Perfil alterado");
}