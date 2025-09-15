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
            System.out.println("Nome de usuário ou senha incorretos.");
        }
        // System.out.println(u.getName());

        // int id = arqUsu.readByCPF(cpf);
        // Usuario u = arqUsu.read(id-1);
        // if (u != null) {
        // System.out.println("Login bem sucedido! Bem-vindo, " + u.getName());
        // } else {
        // System.out.println("Nome de usuário ou senha incorretos.");
        // }
        // System.out.println("Não foi possivel efetuar o login.");
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

    public void menuUsuario() {
        int opcao;
        console = new Scanner(System.in);
        do {
            System.out.println("\n\nEasyGift 1.0");
            System.out.println("---------");
            System.out.println("> Início - Usuário Autenticado");

            System.out.println("\n0 - Sair");
            System.out.println("1 - Adicionar Lista");
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
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0 && opcao != 6);
    }

    public void atualizarSenha()  throws Exception{
        console = new Scanner(System.in);
        System.out.println("\n\nEasyGift 1.0");
        System.out.println("---------");
        System.out.println("> Alteração de Senha - Autenticação de Segurança");
        System.out.println("\nDigite o email cadastrado na sua conta: ");
        String email = console.nextLine();
        System.out.println("Digite sua senha atual: ");
        String senha = console.nextLine();

        ParEmailID par = indiceEmail.read(email.hashCode());

        Usuario u = arqUsu.read(par.getId());

        if(u != null && u.verificarSenha(senha)) {
            System.out.println("Autenticação feita com sucesso!");
            System.out.println("\n\nDigite sua nova senha: ");
            String newSenha = console.nextLine();
            u.alterarSenha(newSenha);
            boolean resultado = arqUsu.update(u);

            if(resultado) {
                System.out.println("Senha atualizada com sucesso!");
            }
            else {
                System.out.println("Houve um problema ao atualizar sua senha, tente novamente.");
            }
        }
        else {
            System.out.println("E-mail ou senha incorretos.");
        }

    }

    public void deletarUsuario() throws Exception{
        console = new Scanner(System.in);
        System.out.println("\n\nEasyGift 1.0");
        System.out.println("---------");
        System.out.println("> Exclusão de Usuário - Autenticação de Segurança");
        System.out.println("\nDigite o email cadastrado na sua conta: ");
        String email = console.nextLine();
        System.out.println("Digite sua senha: ");
        String senha = console.nextLine();

        ParEmailID par = indiceEmail.read(email.hashCode());

        Usuario u = arqUsu.read(par.getId());

        if(u != null && u.verificarSenha(senha)) {
            System.out.println("Verificação feita com sucesso!");
            System.out.println("Você tem certeza que deseja excluir sua conta?(S/N)");
            String resp = console.nextLine();
            if(resp.equals("S")) {
                boolean resposta = arqUsu.delete(par.getId());
                if(resposta) {
                    System.out.println("Conta excluída com sucesso!");
                    return;
                }
                else {
                    System.out.println("Houve um erro ao excluir sua conta, tente novamente mais tarde.");
                }
                
            }
            else {
                return;
            }
        }
    }
}
