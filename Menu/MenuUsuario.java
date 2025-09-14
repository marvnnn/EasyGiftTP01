package Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Arquivos.ArquivoUsuario;
import Entidades.Usuario;
import Entidades.Lista;
import aed3.*;

public class MenuUsuario {
    ArquivoUsuario arqUsu;
    private static Scanner console;
    MenuLista menuLista;
    private HashExtensivel<ParEmailID> indiceEmail;

    public MenuUsuario() throws Exception {
        arqUsu = new ArquivoUsuario();
        console = new Scanner(System.in);
        menuLista = new MenuLista();
        indiceEmail = new HashExtensivel<>(ParEmailID.class.getConstructor(),4,".\\Dados\\usuario\\indiceEmail.d.db", ".\\Dados\\usuario\\indiceEmail.c.db");
    }

    public Usuario buscarPorNomeESenha(String nome, String senha) throws Exception {
        int totalUsuarios = arqUsu.totalUsuariosNoArquivo();
        System.out.println("total de usuarios" + totalUsuarios);
        for (int id = 1; id <= totalUsuarios; id++) {
            Usuario u = arqUsu.read(id-1); // lê cada usuário pelo seu ID
            // if (u != null && u.getName().trim().equalsIgnoreCase(nome.trim())
            //         && u.getPasswordHash().trim().equals(senha.trim())) {
            //     return u; // encontrado
            // }
            if (u!= null) {
                System.out.println("Comparando com usuario: " + u.getNome());

            }else{
                System.out.println("Usuario nulo no id: " + (id-1));
            }
        }
        return null; // usuário não encontrado
    }

    public void logar() throws Exception {
        //Usuario u = arqUsu.read(1);

        System.out.println("Digite o Email: ");
        String email = console.nextLine();
        System.out.println("Digite a senha: ");
        String senha = console.nextLine();

        ParEmailID par = indiceEmail.read(email.hashCode());

        Usuario u = arqUsu.read(par.getId());

        if (u != null && u.verificarSenha(senha)) {
            System.out.println("Login bem sucedido! Bem-vindo, " + u.getNome());
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
        //System.out.println("Digite o CPF: ");
        //String cpf = console.nextLine();
        System.out.println("Digite o nome do usuário: ");
        String name = console.nextLine();
        System.out.println("digite o email: ");
        String email = console.nextLine();
        System.out.println("Digite a senha: ");
        String senha = console.nextLine();
        System.out.println("Digite a pergunta de segurança: ");
        String pergunta = console.nextLine();
        System.out.println("Digite a resposta da pergunta de segurança: ");
        String resposta = console.nextLine();

        //Usuario u = new Usuario(cpf, senha, name, email, pergunta, resposta);
        Usuario u = new Usuario(name, email, senha, pergunta, resposta);
        try {
            int id = arqUsu.create(u);
            indiceEmail.create(new ParEmailID(email,id));
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
            System.out.println("> Início - Autenticado");

            System.out.println("\n0 - Sair");
            System.out.println("1 - Adicionar Lista");
            System.out.println("2 - Remover Lista");
            System.out.println("3 - Ver Listas");

            System.out.print("\nOpção: ");
            opcao = console.nextInt();

            switch (opcao) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        } while (opcao != 0);
    }
}
