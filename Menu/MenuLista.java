package Menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Arquivos.ArquivoLista;
import Entidades.Lista;

public class MenuLista {
    private ArquivoLista arqList;
    private Scanner console;

    public MenuLista() throws Exception {
        arqList = new ArquivoLista();
        console = new Scanner(System.in);
    }

    // Criar lista
    public void criarLista(int idUsuario) throws Exception {
        System.out.println("\n--- Criação de Lista ---");

        System.out.print("Nome da lista: ");
        String nomeList = console.nextLine();

        System.out.print("Descrição da lista: ");
        String desq = console.nextLine();

        System.out.print("Data de exclusão (DD/MM/AAAA): ");
        String dataStr = console.nextLine();

        LocalDate dataExclusao;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataExclusao = LocalDate.parse(dataStr, formatter);
        } catch (Exception e) {
            System.out.println("⚠️ Data inválida! Usando hoje + 30 dias como padrão.");
            dataExclusao = LocalDate.now().plusDays(30);
        }

        Lista lista = new Lista(nomeList, desq, dataExclusao, idUsuario);
        int id = arqList.create(lista);

        System.out.println("\n✅ Lista criada com sucesso! (ID = " + id + ")");
    }

    public void listarListas(int idUsuario) throws Exception {
        System.out.println("\nPresenteFácil 1.0");
        System.out.println("-----------------");
        System.out.println("> Início > Minhas listas\n");

        System.out.println("LISTAS");

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
            System.out.println("Você não possui listas cadastradas.");
        }

        System.out.println("\n(N) Nova lista");
        System.out.println("(R) Retornar ao menu anterior");

        System.out.print("\nOpção: ");
        String opcao = console.nextLine();

        switch (opcao.toUpperCase()) {
            case "N":
                criarLista(idUsuario);
                break;
            case "R":
                return;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    // Ver detalhes de uma lista
    public void verLista(int id) throws Exception {
        Lista lista = arqList.read(id);
        if (lista != null) {
            System.out.println("\n--- Detalhes da Lista ---");
            System.out.println(lista);
        } else {
            System.out.println("\n⚠️ Lista não encontrada.");
        }
    }

    // Editar lista
    public void editarLista(int id) throws Exception {
        Lista lista = arqList.read(id);
        if (lista == null) {
            System.out.println("\n⚠️ Lista não encontrada.");
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
