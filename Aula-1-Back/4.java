import java.util.Scanner;
public class Tabuada {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite um número (1 a 10): ");
        int numero = scanner.nextInt();
        
        if (numero >= 1 && numero <= 10) {
            for (int i = 1; i <= 10; i++){
                System.out.println(numero + "x" + i + "=" + (numero * i));
            }
        }else{
        System.out.println("Número inválido! Digite entre 1 a 10.");
        }
        scanner.close();
    }
}