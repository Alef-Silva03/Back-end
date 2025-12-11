package teste;

public class MainTurma {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Turma turma = new Turma();
			turma.matricular(new Aluno("Ana", 20));
			turma.matricular(new Aluno("Bruno", 22));
			turma.listar();

	}

}
