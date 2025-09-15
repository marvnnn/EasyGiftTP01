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
    private Usuario usuarioLogado;

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
        System.out.print("Email: ");
        String email = console.nextLine();
        System.out.print("Senha: ");
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
            menuUsuario(u.getId());
        } else {
            System.out.println("Email ou senha incorretos.");
        }
    }

    public void registrar() {
        System.out.print("Nome: ");
        String name = console.nextLine();
        System.out.print("Email: ");
        String email = console.nextLine();
        System.out.print("Senha: ");
        String senha = console.nextLine();
        System.out.print("Pergunta de segurança: ");
        String pergunta = console.nextLine();
        System.out.print("Resposta da pergunta: ");
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

    public void menuUsuario(int idUsuario) throws Exception {
        int opcao;
        do {
            System.out.println("\n--- Menu do Usuário ---");
            System.out.println("1 - Criar Lista");
            System.out.println("2 - Excluir Lista");
            System.out.println("3 - Ver Listas");
            System.out.println("0 - Sair");

            System.out.print("Opção: ");
            opcao = Integer.parseInt(console.nextLine());

            switch (opcao) {
                case 1:
                    menuLista.criarLista(idUsuario);
                    break;
                case 2:
                    System.out.print("Digite o ID da lista a excluir: ");
                    int idExcluir = Integer.parseInt(console.nextLine());
                    menuLista.excluirLista(idExcluir);
                    break;
                case 3:
                    menuLista.listarListas(idUsuario);
                    break;
                case 0:
                    usuarioLogado = null;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
