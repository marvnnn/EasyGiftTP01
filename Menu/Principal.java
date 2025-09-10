package Menu;

import java.util.Scanner;
import Arquivos.*;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner;
        ArquivoUsuario arqUsu = new ArquivoUsuario();

        try {
            scanner = new Scanner(System.in);
            int option;
            do {
                System.out.println("\n\nEasyGift 1.0");
                System.out.println("---------");
                System.out.println("> Início");

                System.out.println("\n0 - Sair");
                System.out.println("1 - Login");
                System.out.println("2 - Registrar-se");

                System.out.print("\nOpção: ");

                option = scanner.nextInt();

                switch (option) {
                    case 0:
                        break;
                    case 1: System.out.println("Digite o nome do usuário: "); String name = scanner.nextLine(); System.out.println("Digite a senha: ");
                    String senha = scanner.nextLine();
                        
                
                    default:
                        break;
                }

            } while(option != 0);
        }
        scanner.close();
    }

    public void logar() {

    }

    public int registrar() {
        
    }
}
