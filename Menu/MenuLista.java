package Menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Arquivos.ArquivoLista;
import Arquivos.ArquivoUsuario;
import Entidades.Lista;
import Entidades.Usuario;

public class MenuLista {
    private ArquivoLista arqList;
    private ArquivoUsuario arqUsu;
    private Scanner console;

    public MenuLista() throws Exception {
        arqUsu = new ArquivoUsuario();
        arqList = new ArquivoLista();
        console = new Scanner(System.in);
    }

    // Criar lista
    public void criarLista(int idUsuario) throws Exception {
        Usuario u = arqUsu.read(idUsuario);
        System.out.println("\n\n\n---------");
        System.out.println("> Listas - Criação de Listas");        
        System.out.println("Nome da Lista: ");
        String nomeList = console.nextLine();

        System.out.print("Descrição da Lista: ");
        String desq = console.nextLine();

        System.out.print("Data de exclusão (DD/MM/AAAA): ");
        String dataStr = console.nextLine();

        LocalDate dataExclusao;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataExclusao = LocalDate.parse(dataStr, formatter);
        } catch (Exception e) {
            System.out.println("Data inválida! Usando hoje + 30 dias como padrão.");
            dataExclusao = LocalDate.now().plusDays(30);
        }

        Lista lista = new Lista(nomeList, desq, dataExclusao, u.getNome(), idUsuario);
        int id = arqList.create(lista);
 
        System.out.println("\nLista criada com sucesso! (ID = " + id + ")");
    }

    public int listarListasUsuario(int idUsuario) throws Exception {
        System.out.println("\n\n---------");
        System.out.println("> Listas > Minhas Listas\n");

        int count = 0;
        int index = 1; // contador para exibição numerada

        for (int i = 0; i < arqList.tamanho(); i++) {
            Lista lista = arqList.read(i + 1); // IDs começam em 1
            if (lista != null && lista.getIdUsuario() == idUsuario) {
                String dataStr = lista.getDataLimite() != null
                        ? lista.getDataLimite().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        : "Sem data limite";
                System.out.println("(" + index + ") " + lista.getNome() + " - " + dataStr);
                index++;
                count++;
            }
        }

        if (count == 0) {
            System.out.println("Você não possui Listas cadastradas.");
        }

        System.out.println("Digite a opção: ");
        int id = console.nextInt();

        return id;
    }

    public int listarListas(int idUsuario) throws Exception {
        System.out.println("\n\n---------");
        System.out.println("> Listas > Todas as Listas\n");
        System.out.println("---------");

        int count = 0;
        int index = 1; // contador para exibição numerada

        for (int i = 0; i < arqList.tamanho(); i++) {
            Lista lista = arqList.read(i + 1); // IDs começam em 1
            if (lista != null && lista.getIdUsuario() != idUsuario) {
                String dataStr = lista.getDataLimite() != null
                        ? lista.getDataLimite().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        : "Sem data limite";
                System.out.println("(" + index + ") " + lista.getNome() + " - " + lista.getNomeAutor() +  " - " + dataStr);
                index++;
                count++;
            }
        }

        if (count == 0) {
            System.out.println("Você não possui Listas cadastradas.");
        }

        System.out.println("Digite a opção: ");
        int id = console.nextInt();

        return id;
    }

    // Ver detalhes de uma lista
    public void verLista(int id) throws Exception {
        Lista lista = arqList.read(id);
        if (lista != null) {
            System.out.println("\n--- Detalhes da Lista ---");
            System.out.println(lista);
        } else {
            System.out.println("\nLista não encontrada.");
        }
    }

    // Editar lista
    public void editarLista(int id) throws Exception {
        Lista lista = arqList.read(id);
        if (lista == null) {
            System.out.println("\nLista não encontrada.");
            return;
        }

        System.out.println("\n--- Editando Lista ---");
        System.out.println("Nome atual: " + lista.getNome());
        System.out.print("Novo nome (ou Enter p/ manter): ");
        String novoNome = console.nextLine();
        if (!novoNome.isEmpty())
            lista.setNome(novoNome);

        System.out.println("Descrição atual: " + lista.getDescricao());
        System.out.print("Nova descrição (ou Enter p/ manter): ");
        String novaDesc = console.nextLine();
        if (!novaDesc.isEmpty())
            lista.setDescricao(novaDesc);

        System.out.println("Data limite atual: " + lista.getDataLimite());
        System.out.print("Nova data limite (DD/MM/AAAA ou Enter p/ manter): ");
        String novaDataStr = console.nextLine();
        if (!novaDataStr.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate novaData = LocalDate.parse(novaDataStr, formatter);
                lista.setDataLimite(novaData);
            } catch (Exception e) {
                System.out.println("⚠️ Data inválida! Mantendo a anterior.");
            }
        }

        if (arqList.update(lista)) {
            System.out.println("\n✅ Lista atualizada com sucesso!");
        } else {
            System.out.println("\n⚠️ Erro ao atualizar lista.");
        }
    }

    // Excluir lista
    public void excluirLista(int id) throws Exception {
        if (arqList.delete(id)) {
            System.out.println("\n✅ Lista excluída com sucesso!");
        } else {
            System.out.println("\n⚠️ Lista não encontrada.");
        }
    }
}
