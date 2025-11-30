// java
package Client;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuClient {
    private Scanner sc;
    private Controller controller;


    public MenuClient(Scanner sc,Controller controller) {
        this.sc = sc;
        this.controller = controller;
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

        menu.setHandler(0, () -> sair[0] = true);

        do {
            menu.run();
        } while (!sair[0]);
    }

    private void doRegisterEvent() {
        System.out.println("Registar evento selecionado");
        System.out.println("Indica o nome do produto: ");
        String produto = sc.nextLine();
        System.out.println("Indica a quantidade: ");
        int quant = 0;
        boolean numeroValido = false;
        while (!numeroValido) {
            try {
                quant = Integer.parseInt(sc.nextLine());
                numeroValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Escreve um número válido.");
            }
        }
        System.out.println("Indica o preço: ");
        float preco = 0;
        numeroValido = false;
        while (!numeroValido) {
            try {
                preco = Float.parseFloat(sc.nextLine());
                numeroValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Escreve um número válido.");
            }
        }
        controller.RegisterEvent(produto, quant, preco);
    }

    private void startNewDay() {
        System.out.println("Começar novo dia selected");
        controller.newDay();
    }

    private void getSalesQuantity() {
        System.out.println("Quantidade de vendas selected");
        System.out.println("Indica o nome do produto: ");
        String produto = sc.nextLine();
        System.out.println("Indica o numero de dias a analizar: ");
        int dia = 0;
        boolean numeroValido = false;
        while (!numeroValido) {
            try {
                dia = Integer.parseInt(sc.nextLine());
                numeroValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Escreve um número válido.");
            }
        }
        controller.getSalesQuantity(produto, dia);
    }

    private void getSalesVolume() {
        System.out.println("Volume de vendas selected");
        System.out.println("Indica o nome do produto: ");
        String produto = sc.nextLine();
        System.out.println("Indica o numero de dias a analizar: ");
        int dia = 0;
        boolean numeroValido = false;
        while (!numeroValido) {
            try {
                dia = Integer.parseInt(sc.nextLine());
                numeroValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Escreve um número válido.");
            }
        }
        controller.getSalesVolume(produto, dia);
    }

    private void getAverageSalesPrice() {
        System.out.println("Preço médio de venda selected");
        System.out.println("Indica o nome do produto: ");
        String produto = sc.nextLine();
        System.out.println("Indica o numero de dias a analizar: ");
        int dia = 0;
        boolean numeroValido = false;
        while (!numeroValido) {
            try {
                dia = Integer.parseInt(sc.nextLine());
                numeroValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Escreve um número válido.");
            }
        }
        controller.getAverageSalesPrice(produto, dia);
    }

    private void getMaxSalesPrice() {
        System.out.println("Preço máximo de venda selected");
        System.out.println("Indica o nome do produto: ");
        String produto = sc.nextLine();
        System.out.println("Indica o numero de dias a analizar: ");
        int dia = 0;
        boolean numeroValido = false;
        while (!numeroValido) {
            try {
                dia = Integer.parseInt(sc.nextLine());
                numeroValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Escreve um número válido.");
            }
        }
        controller.getMaxSalesPrice(produto, dia);
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

        System.out.println("Indica o dia a analizar: ");
        int dia = 0;
        boolean numeroValido = false;
        while (!numeroValido) {
            try {
                dia = Integer.parseInt(sc.nextLine());
                numeroValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Escreve um número válido.");
            }
        }
        controller.filterEvents(produtos, dia);
    }

    private void notifySimultaneousSales() {
        System.out.println("Notificação: vendas simultâneas selected");
        System.out.println("Indica o nome do primeiro produto: ");
        String produto1 = sc.nextLine();
        System.out.println("Indica o nome do segundo produto: ");
        String produto2 = sc.nextLine();
        controller.notifySimultaneousSales(produto1, produto2);
    }

    private void notifyConsecutiveSales() {
        System.out.println("Notificação: vendas consecutivas selected");
        System.out.println("Indica o número de vendas consecutivas: ");
        int n = 0;
        boolean numeroValido = false;
        while (!numeroValido) {
            try {
                n = Integer.parseInt(sc.nextLine());
                numeroValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Escreve um número válido.");
            }
        }
        controller.notifyConsecutiveSales(n);
    }

    public static void main(String[] args) {
        MenuClient mc = new MenuClient(new Scanner(System.in), new Controller());
        mc.run();
    }
}