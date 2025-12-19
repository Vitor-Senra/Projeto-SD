package Server;

import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        Loja loja = new LojaImpl();
        ServerSocket ss;
        try{
            ss = new ServerSocket(12345);
        } catch (Exception e){
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
            return;
        }

        while (true) {
            try {
                ss.accept();
            } catch (Exception e) {
                System.out.println("Erro ao aceitar conexão: " + e.getMessage());
            }
        }
    }

}
