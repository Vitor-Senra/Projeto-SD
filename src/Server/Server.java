package Server;

import Aux.Message;
import Conn.Connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final int nThreads = 5;

    public static void main(String[] args) {
        Map<Integer, Skeleton> skeletons = new HashMap<>();
        skeletons.put(0, new LojaSkeleton(new LojaImpl()));
        ServerSocket ss;

        try{
            ss = new ServerSocket(12345);
        } catch (Exception e){
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
            return;
        }

        while (true) {
            try {
                Socket s = ss.accept();
                Thread clientHandler = new Thread(() -> {
                    try{
                        Connection conn = new Connection(s);
                        while (true) {
                            Message msg = conn.receive();
                            Skeleton skel = skeletons.get(msg.getSkeletonId());
                            new Thread(() -> {
                                try {
                                    skel.Handle(msg, conn);
                                } catch (Exception e) {
                                    System.out.println("Erro ao processar pedido: " + e.getMessage());
                                }
                            }).start();
                        }
                    } catch (java.io.EOFException e) {
                        System.out.println("Cliente desconectou-se (EOF).");
                    } catch (Exception e) {
                        System.out.println("Erro na conexão com cliente: " + e.getMessage());
                    } finally {
                        // Garantir que fechamos o socket quando a thread termina
                        try { s.close(); } catch (Exception ignored) {}
                    }
                });
                clientHandler.start();
            } catch (Exception e) {
                System.out.println("Erro ao aceitar conexão: " + e.getMessage());
            }
        }
    }

}
