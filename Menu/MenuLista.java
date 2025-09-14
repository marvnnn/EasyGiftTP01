package Menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;

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
    public int criarLista(int idUsuario) throws Exception {
        System.out.println("\n\nEasyGift 1.0");
        System.out.println("---------");
        System.out.println("> Criação de Lista");

        System.out.print("\nDigite o nome da lista: ");
        String nomeList = console.nextLine();

        System.out.print("Digite a descrição da lista: ");
        String desq = console.nextLine();

        System.out.print("Digite quando a lista deverá ser excluída (DD/MM/AAAA): ");
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
        return id;
    }

    // Ler lista
    public void verLista(int id) throws Exception {
        Lista lista = arqList.read(id);
        if (lista != null) {
            System.out.println("\n--- Detalhes da Lista ---");
            System.out.println(lista);
        } else {
            System.out.println("\n⚠️ Lista não encontrada.");
        }
    }

    // Atualizar lista
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
        if (!novoNome.isEmpty()) lista.setNome(novoNome);

        System.out.println("Descrição atual: " + lista.getDescricao());
        System.out.print("Nova descrição (ou Enter p/ manter): ");
        String novaDesc = console.nextLine();
        if (!novaDesc.isEmpty()) lista.setDescricao(novaDesc);

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
            System.out.println("\n⚠️ Erro: Lista não encontrada.");
        }
    }

    // Menu principal
    public void menuListas(int idUsuario) throws Exception {
        int opcao;
        do {
            System.out.println("\n\nEasyGift 1.0");
            System.out.println("---------");
            System.out.println("> Minhas Listas");
            System.out.println("1 - Criar Lista");
            System.out.println("2 - Ver Lista");
            System.out.println("3 - Editar Lista");
            System.out.println("4 - Excluir Lista");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            opcao = Integer.parseInt(console.nextLine());

            switch (opcao) {
                case 1:
                    criarLista(idUsuario);
                    break;
                case 2:
                    System.out.print("Digite o ID da lista: ");
                    int idVer = Integer.parseInt(console.nextLine());
                    verLista(idVer);
                    break;
                case 3:
                    System.out.print("Digite o ID da lista: ");
                    int idEdit = Integer.parseInt(console.nextLine());
                    editarLista(idEdit);
                    break;
                case 4:
                    System.out.print("Digite o ID da lista: ");
                    int idDel = Integer.parseInt(console.nextLine());
                    excluirLista(idDel);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("⚠️ Opção inválida!");
            }
        } while (opcao != 0);
    }
}
