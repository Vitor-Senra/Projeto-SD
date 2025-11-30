package Client;

import java.util.Scanner;
import java.util.function.BooleanSupplier;

/**
 * Classe de menu reutilizável com suporte a handlers (lambdas) e precondições.
 * Permite associar ações (handlers) a opções de menu e controlar se cada uma
 * está ativa via precondições booleanas.
 */
public class Menu {
    private String[] opcoes; // Texto das opções do menu
    private Runnable[] handlers; // Ações associadas a cada opção
    private BooleanSupplier[] preconds; // Precondições de execução por opção
    private Scanner sc; // Scanner para entrada do utilizador

    /**
     * Construtor do menu.
     * @param opcoes Array de strings com os textos das opções a apresentar.
     */
    public Menu(String[] opcoes) {
        this.opcoes = opcoes;
        this.handlers = new Runnable[opcoes.length + 1];
        this.preconds = new BooleanSupplier[opcoes.length + 1];
        for (int i = 0; i < preconds.length; i++) {
            preconds[i] = () -> true; // Por defeito, todas as opções estão ativas
        }
        this.sc = new Scanner(System.in);
    }

    /**
     * Define a ação (handler) associada a uma opção.
     * @param opc Índice da opção (0 = primeira opção)
     * @param handler Runnable com a ação a executar
     */
    public void setHandler(int opc, Runnable handler) {
        if (opc >= 0 && opc < handlers.length) {
            handlers[opc] = handler;
        }
    }

    /**
     * Define a precondição que controla a ativação da opção.
     * @param opc Índice da opção (deve ser ≥ 1)
     * @param cond Função booleana (sem argumentos) que determina se a opção está ativa
     */
    public void setPreCondition(int opc, BooleanSupplier cond) {
        if (opc >= 1 && opc < preconds.length) {
            preconds[opc] = cond;
        }
    }

    /**
     * Executa o menu: apresenta opções, lê a escolha do utilizador e executa o handler se possível.
     */
    public void run() {
        int opc;
        show();
        opc = lerOpcao();
        if (opc >= 0 && opc < handlers.length && preconds[opc].getAsBoolean()) {
            Runnable h = handlers[opc];
            if (h != null) h.run();
        } else {
            System.out.println("Escolha uma opção válida!");
        }
    }

    /**
     * Mostra as opções do menu, ocultando as desativadas.
     */
    private void show() {
        for (int i = 0; i < opcoes.length; i++) {
            if (preconds[i].getAsBoolean()) {
                System.out.printf("%s\n", opcoes[i]);
            } else {
                System.out.println("-----");
            }
        }
    }

    /**
     * Lê a opção escolhida pelo utilizador.
     * @return índice da opção escolhida ou -1 se a entrada for inválida
     */
    private int lerOpcao() {
        System.out.print("Opção: ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
