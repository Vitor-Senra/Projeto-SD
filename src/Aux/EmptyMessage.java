package Aux;

import Conn.Type;
import java.io.*;

public class EmptyMessage extends Message {
    public EmptyMessage(int id, int skeletonId, Type type) {
        super(id, skeletonId, type);
    }

    @Override
    public void writeContent(DataOutputStream out) throws IOException {
        // Não escreve nada
    }
}