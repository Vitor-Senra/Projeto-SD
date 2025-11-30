package Client;

import java.util.Scanner;
import Exceptions.*;

public class MenuPrincipal implements Runnable {
    Scanner sc;
    private Controller controller;

    public MenuPrincipal() {
        this.sc = new Scanner(System.in);
        this.controller = new Controller();
    }

    public void run () {
        System.out.println("Client is running...");

        boolean sair[] = {false};
        Menu menu = new Menu(new String[] {
                "\n======== MENU ========",
                "1- Login",
                "2- Registar",
                "0- Sair"
        });

        menu.setHandler(1, this::doLogin);
        menu.setHandler(2, this::doRegister);
        menu.setHandler(0, () -> sair[0] = true);

        do {
            menu.run();
        } while (!sair[0]);
    }

    private void doLogin() {
        System.out.println("Login selected");
        System.out.println("Indica o teu username: ");
        String username = sc.nextLine();
        System.out.println("Indica a tua password: ");
        String password = sc.nextLine();
        try{
            controller.fazerLogin(username, password);
            System.out.println("Login efectuado com sucesso!");
            MenuClient menuCliente = new MenuClient(sc, controller);
            menuCliente.run();
        } catch (InvalidCredentialsException e){
            System.out.println("Credenciais inválidas. Tenta novamente.");
            return;
        }

    }

    private void doRegister() {
        System.out.println("Login selected");
        System.out.println("Indica o teu username: ");
        String username = sc.nextLine();
        System.out.println("Indica a tua password: ");
        String password = sc.nextLine();
        try{
            controller.fazerRegisto(username, password);
            System.out.println("Registo efectuado com sucesso!");
        } catch (UsernameAlreadyExistsException e){
            System.out.println("Username já existe. Tenta novamente.");
            return;
        }
    }
}
