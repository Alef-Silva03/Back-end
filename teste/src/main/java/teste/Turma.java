package teste;
import java.util.ArrayList;

public class Turma {
	private ArrayList<Aluno> alunos = new ArrayList<>();
	
	public void matricular (Aluno aluno) {
		alunos.add(aluno);
	}
	public void listar() {
		for (Aluno a : alunos) {
			System.out.println(a);
		}
	}
}
