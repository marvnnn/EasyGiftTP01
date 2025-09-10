package Menu;

import java.util.Scanner;

public class MenuUsuario {
    ArquivoUsuario arqUsu;
    private static Scanner console = new Scanner(System.in);

    public MenuUsuario() throws Exception {
        arqUsu = new ArquivoUsuario();
    }

    public void menu() {

        int option;
        do {
            System.out.println("\n");
        } while(option != 0);
    }
}
