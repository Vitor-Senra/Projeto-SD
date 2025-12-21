package Client;

import java.io.IOException;
import java.util.Scanner;

import Aux.SharedData;
import Exceptions.*;
import Server.Loja;

public class MenuPrincipal implements Runnable {
    Scanner sc;
    private Loja stub;
    private boolean ErroConexao = false;

    public MenuPrincipal() {
        this.sc = new Scanner(System.in);
        try {
            this.stub = new LojaStub();
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao servidor: " + e.getMessage());
            ErroConexao = true;
        }
    }

    public void run () {
        System.out.println("Client is running...");

        boolean sair[] = {false};
        Menu menu = new Menu(new String[] {
                "\n======== MENU ========",
                "1- Login",
                "2- Registar",
                "3- Reconectar ao Servidor",
                "0- Sair"
        });

        menu.setHandler(1, this::doLogin);
        menu.setHandler(2, this::doRegister);
        menu.setHandler(3, this::reconnect);
        menu.setHandler(0, () -> sair[0] = true);

        menu.setPreCondition(1,() -> !ErroConexao);
        menu.setPreCondition(2,() -> !ErroConexao);
        menu.setPreCondition(3,() -> ErroConexao);

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
            stub.fazerLogin(username, password);
            MenuClient menuCliente = new MenuClient(sc, stub);
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
            stub.fazerRegisto(username, password);
            System.out.println("Registo efectuado com sucesso!");
        } catch (UsernameAlreadyExistsException e){
            System.out.println("Username já existe. Tenta novamente.");
            return;
        }
    }

    private void reconnect() {
        System.out.println("Reconectar ao Servidor selected");
        try {
            this.stub = new LojaStub();
            ErroConexao = false;
            System.out.println("Reconexão bem sucedida!");
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao servidor: " + e.getMessage());
            ErroConexao = true;
        }
    }
}
