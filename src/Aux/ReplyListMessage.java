package Aux;

import Conn.Type;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReplyListMessage extends Message {
    private List<String> lista;

    public ReplyListMessage(int id, int skeletonId, Type type, List<String> lista) {
        super(id, skeletonId, type);
        this.lista = lista;
    }

    public ReplyListMessage(int id, int skeletonId, List<String> lista) {
        super(id, skeletonId, Type.REPLY_LIST);
        this.lista = lista;
    }

    @Override
    public void writeContent(DataOutputStream out) throws IOException {
        if (lista == null) {
            out.writeInt(0);
        } else {
            out.writeInt(lista.size());
            for (String s : lista) {
                out.writeUTF(s);
            }
        }
    }

    public static ReplyListMessage deserialize(DataInputStream in, int id, int skeletonId) throws IOException {
        int size = in.readInt();
        List<String> l = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            l.add(in.readUTF());
        }
        return new ReplyListMessage(id, skeletonId, Type.REPLY_LIST, l);
    }

    public List<String> getLista() { return lista; }
}