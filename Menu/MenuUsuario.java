package Menu;

import java.util.Scanner;

import Arquivos.ArquivoUsuario;
import Entidades.Usuario;
import aed3.HashExtensivel;
import aed3.ParEmailID;

public class MenuUsuario {
    private ArquivoUsuario arqUsu;
    private static Scanner console;
    private MenuLista menuLista;
    private HashExtensivel<ParEmailID> indiceEmail;
    private Usuario usuarioLogado; // mantém usuário autenticado

    public MenuUsuario() throws Exception {
        arqUsu = new ArquivoUsuario();
        console = new Scanner(System.in);
        menuLista = new MenuLista();
        indiceEmail = new HashExtensivel<>(
                ParEmailID.class.getConstructor(),
                4,
                ".\\Dados\\usuario\\indiceEmail.d.db",
                ".\\Dados\\usuario\\indiceEmail.c.db"
        );
    }

    public void logar() throws Exception {
        System.out.println("Digite o Email: ");
        String email = console.nextLine();
        System.out.println("Digite a senha: ");
        String senha = console.nextLine();

        ParEmailID par = indiceEmail.read(email.hashCode());

        if (par == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        Usuario u = arqUsu.read(par.getId());

        if (u != null && u.verificarSenha(senha)) {
            usuarioLogado = u;
            System.out.println("Login bem sucedido! Bem-vindo, " + u.getNome());
            menuUsuario();
        } else {
            System.out.println("Email ou senha incorretos.");
        }
    }

    public void registrar() {
        console = new Scanner(System.in);

        System.out.println("Digite o nome do usuário: ");
        String name = console.nextLine();
        System.out.println("Digite o email: ");
        String email = console.nextLine();
        System.out.println("Digite a senha: ");
        String senha = console.nextLine();
        System.out.println("Digite a pergunta de segurança: ");
        String pergunta = console.nextLine();
        System.out.println("Digite a resposta da pergunta de segurança: ");
        String resposta = console.nextLine();

        Usuario u = new Usuario(name, email, senha, pergunta, resposta);
        try {
            int id = arqUsu.create(u);
            indiceEmail.create(new ParEmailID(email, id));
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public void menuUsuario() throws Exception {
        int opcao;
        console = new Scanner(System.in);
        do {
            System.out.println("\n\nEasyGift 1.0");
            System.out.println("---------");
            System.out.println("> Início - Usuário Autenticado");

            System.out.println("\n0 - Sair");
            System.out.println("1 - Criar Lista");
            System.out.println("2 - Remover Lista");
            System.out.println("3 - Ver Listas");

            System.out.print("\nOpção: ");
            opcao = console.nextInt();
            console.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 0:
                    usuarioLogado = null; // desloga
                    break;
                case 1:
                    menuLista.criarLista(usuarioLogado.getId()); // passa o id do usuário logado
                    break;
                case 2:
                    menuLista.excluirLista(usuarioLogado.getId());
                    break;
                case 3:
                    menuLista.verLista(usuarioLogado.getId());
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }
}
