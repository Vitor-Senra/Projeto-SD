// java
package Client;

import Server.Loja;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuClient {
    private Scanner sc;
    private Loja stub;


    public MenuClient(Scanner sc, Loja controller) {
        this.sc = sc;
        this.stub = controller;
    }

    public void run() {

        boolean sair[] = {false};
        Menu menu = new Menu(new String[]{
                "0 - Logout",
                "1 - Registar evento (produto, quantidade, preço)",
                "2 - Começar novo dia",
                "3 - Quantidade de vendas (produto, d)",
                "4 - Volume de vendas (produto, d)",
                "5 - Preço médio de venda (produto, d)",
                "6 - Preço máximo de venda (produto, d)",
                "7 - Filtrar eventos (dia d, lista produtos separada por ,)",
                "8 - Notificação: vendas simultâneas (p1,p2)",
                "9 - Notificação: vendas consecutivas (n)",
                "10- Mostrar respostas pendentes"
        });

        // Handlers
        menu.setHandler(1, this::doRegisterEvent);

        menu.setHandler(2, this::startNewDay);

        menu.setHandler(3, this::getSalesQuantity);

        menu.setHandler(4, this::getSalesVolume);

        menu.setHandler(5, this::getAverageSalesPrice);

        menu.setHandler(6, this::getMaxSalesPrice);

        menu.setHandler(7, this::filterEvents);

        menu.setHandler(8, this::notifySimultaneousSales);

        menu.setHandler(9, this::notifyConsecutiveSales);

        menu.setHandler(10, this::showPendingReplies);

        menu.setHandler(0, () -> {

                sair[0] = true;
                this.logout();
        });

        menu.setPreCondition(10, () -> this.hasPendingReplies());

        do {
            menu.run();
        } while (!sair[0]);
    }

    private void doRegisterEvent() {
        System.out.println("Registar evento selecionado");
        System.out.println("Indica o nome do produto: ");
        String produto = sc.nextLine();
        int quant = readInt("Indica a quantidade: ");
        float preco = readFloat("Indica o preço: ");
        stub.RegisterEvent(produto, quant, preco);
    }

    private void startNewDay() {
        System.out.println("Começar novo dia selected");
        stub.newDay();
    }

    private void getSalesQuantity() {
        System.out.println("Quantidade de vendas selected");
        System.out.println("Indica o nome do produto: ");
        String produto = sc.nextLine();
        int dia = readInt("Indica o numero de dias a analizar: ");
        stub.getSalesQuantity(produto, dia);
    }

    private void getSalesVolume() {
        System.out.println("Volume de vendas selected");
        System.out.println("Indica o nome do produto: ");
        String produto = sc.nextLine();
        int dia = readInt("Indica o numero de dias a analizar: ");
        stub.getSalesVolume(produto, dia);
    }

    private void getAverageSalesPrice() {
        System.out.println("Preço médio de venda selected");
        System.out.println("Indica o nome do produto: ");
        String produto = sc.nextLine();
        int dia = readInt("Indica o numero de dias a analizar: ");
        stub.getAverageSalesPrice(produto, dia);
    }

    private void getMaxSalesPrice() {
        System.out.println("Preço máximo de venda selected");
        System.out.println("Indica o nome do produto: ");
        String produto = sc.nextLine();
        int dia = readInt("Indica o numero de dias a analizar: ");
        stub.getMaxSalesPrice(produto, dia);
    }

    private void filterEvents() {
        System.out.println("Filtrar eventos selected");
        System.out.print("Insira os nomes dos produtos (separados por vírgula): ");
        String linha = sc.nextLine();

        List<String> produtos = Arrays.stream(linha.split(","))
                .map(String::trim)
                .distinct()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        int dia = readInt("Indica o dia a analizar: ");
        stub.filterEvents(produtos, dia);
    }

    private void notifySimultaneousSales() {
        System.out.println("Notificação: vendas simultâneas selected");
        System.out.println("Indica o nome do primeiro produto: ");
        String produto1 = sc.nextLine();
        System.out.println("Indica o nome do segundo produto: ");
        String produto2 = sc.nextLine();
        stub.notifySimultaneousSales(produto1, produto2);
    }

    private void notifyConsecutiveSales() {
        System.out.println("Notificação: vendas consecutivas selected");
        int n = readInt("Indica o número de vendas consecutivas: ");
        stub.notifyConsecutiveSales(n);
    }

    private void showPendingReplies (){
        System.out.println("Respostas pendentes:");
        LojaStub Stub = (LojaStub) stub;
        System.out.println(Stub.getPendingReplies());
    }

    private boolean hasPendingReplies() {
        LojaStub Stub = (LojaStub) stub;
        return Stub.hasPendingReplies();
    }

    private void logout() {
        try{
            LojaStub Stub = (LojaStub) stub;
            Stub.close();
        } catch (IOException e){
            System.out.println("Erro ao fechar a ligação com o servidor.");
        }
    }

    ///////////////////////////////////////////////////////  Métodos auxiliares de leitura  ///////////////////////////////////////////////////////
    private int readInt(String prompt) {
        int value = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(sc.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Escreve um número válido.");
            }
        }
        return value;
    }

    private float readFloat(String prompt) {
        float value = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                value = Float.parseFloat(sc.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Escreve um número válido.");
            }
        }
        return value;
    }

}