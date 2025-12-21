package Aux;

import Conn.Type;
import java.io.*;

public class RegisterEventMessage extends Message {
    private String produto;
    private int quantidade;
    private double preco;

    public RegisterEventMessage(int id, int skeletonId, Type type, String produto, int quantidade, double preco) {
        super(id, skeletonId, type);
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public RegisterEventMessage(int id, int skeletonId, String produto, int quantidade, double preco) {
        super(id, skeletonId, Type.REGISTER_EVENT);
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    @Override
    public void writeContent(DataOutputStream out) throws IOException {
        out.writeUTF(produto);
        out.writeInt(quantidade);
        out.writeDouble(preco);
    }

    public static RegisterEventMessage deserialize(DataInputStream in, int id, int skeletonId) throws IOException {
        return new RegisterEventMessage(id, skeletonId, in.readUTF(), in.readInt(), in.readDouble());
    }

    public String getProduct() { return produto; }
    public int getQuantity() { return quantidade; }
    public double getPrice() { return preco; }
}