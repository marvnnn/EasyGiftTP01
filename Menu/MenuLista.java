package Menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Arquivos.ArquivoLista;
import Entidades.Lista;

public class MenuLista {
    ArquivoLista arqList;
    private Scanner console;

    public MenuLista() {
        //arqList = new ArquivoLista();
        console = new Scanner(System.in);
    }

    public int criarLista() throws Exception {
    System.out.println("\n\nEasyGift 1.0");
    System.out.println("---------");
    System.out.println("> Criação de Listas");

    System.out.print("\nDigite o nome da lista: ");
    String nomeList = console.nextLine();

    System.out.print("Digite a descrição da lista: ");
    String desq = console.nextLine();

    System.out.print("Digite quando a lista deverá ser excluída (DD/MM/AAAA): ");
    String dataStr = console.nextLine();

    LocalDate dataExclusao = null;
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dataExclusao = LocalDate.parse(dataStr, formatter);
    } catch (Exception e) {
        System.out.println("⚠️ Data inválida! Usando hoje + 30 dias como padrão.");
        dataExclusao = LocalDate.now().plusDays(30);
    }

    // Aqui você pode criar um objeto Lista (se tiver a classe) e armazenar
    // Exemplo fictício:
    Lista lista = new Lista(nomeList, desq, LocalDate.now(), dataExclusao);

    // Supondo que salvar retorne um ID
    int id = arqList.create(lista);

    System.out.println("\nLista criada com sucesso!" );

    return id;
}



}
