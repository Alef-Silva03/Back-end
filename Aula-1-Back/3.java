import java.util.Scanner;
public class Salario {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o seu salario: ");
        float salario = scanner.nextFloat();// float ou int nextFloat ou nextInt
        System.out.println("Valor descontado: " + (salario * 0.10));
        System.out.println("Salário com desconto: " + (salario * 0.9));
        scanner.close();
    }
    
}