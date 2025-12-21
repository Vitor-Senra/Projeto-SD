package Aux;

import Conn.Type;
import java.io.*;

public class AuthMessage extends Message {
    private String username;
    private String password;

    public AuthMessage(int id, int skeletonId, Type type, String username, String password) {
        super(id, skeletonId, type);
        this.username = username;
        this.password = password;
    }

    @Override
    public void writeContent(DataOutputStream out) throws IOException {
        out.writeUTF(username);
        out.writeUTF(password);
    }

    public static AuthMessage deserialize(DataInputStream in, int id, int skeletonId, Type type) throws IOException {
        return new AuthMessage(id, skeletonId, type, in.readUTF(), in.readUTF());
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}