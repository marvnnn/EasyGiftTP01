package Menu;

import java.io.IOException;
import java.util.Scanner;

import Arquivos.ArquivoUsuario;
import Entidades.Usuario;
import Entidades.Lista;

public class MenuUsuario {
    ArquivoUsuario arqUsu;

    private static Scanner console = new Scanner(System.in);

    public MenuUsuario() throws Exception {
        arqUsu = new ArquivoUsuario();
    }

    public Usuario buscarPorNomeESenha(String nome, String senha) throws Exception {
        int totalUsuarios = arqUsu.totalUsuariosNoArquivo();
       System.out.println("totla de usuarios" + totalUsuarios);
        for (int id = 1; id <= totalUsuarios; id++) {
            Usuario u = arqUsu.read(id); // lê cada usuário pelo seu ID
            if (u != null && u.getName().trim().equalsIgnoreCase(nome.trim()) && u.getPasswordHash().trim().equals(senha.trim()))  {
                return u; // encontrado
            }
        }
        return null; // usuário não encontrado
    }

    public void logar() {

        System.out.println("Digite o nome do usuário: ");
        String name = console.nextLine();
        System.out.println("Digite a senha: ");
        String senha = console.nextLine();

        try {
            Usuario u = buscarPorNomeESenha(name, senha);
            if (u != null) {
                System.out.println("Login bem sucedido! Bem-vindo, " + u.getName());
            } else {
                System.out.println("Nome de usuário ou senha incorretos.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }

    }

    public void registrar() {

        System.out.println("Digite o CPF: ");
        String cpf = console.nextLine();
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

        Usuario u = new Usuario(cpf, senha, name, email, pergunta, resposta);
        try {
            int id = arqUsu.create(u);
            System.out.println("Usuário cadastrado com sucesso!" + id);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }

    }
}
