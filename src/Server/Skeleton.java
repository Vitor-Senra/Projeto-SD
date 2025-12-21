package Server;

import Aux.Message;
import Conn.Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface Skeleton {
    public void Handle(Message msg, Connection conn) throws Exception;
}
