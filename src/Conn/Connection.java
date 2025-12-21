package Conn;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import Aux.Message;

public class Connection implements AutoCloseable {
    Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Lock readLock = new ReentrantLock();
    private Lock writeLock = new ReentrantLock();

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void send(Message mensagem) throws IOException {
        writeLock.lock();
        try {
            mensagem.serialize(out);
            out.flush();
        } finally {
            writeLock.unlock();
        }
    }

    public Message receive() throws IOException {
        readLock.lock();
        try{
            return Message.deserialize(in);
        }   finally{
            readLock.unlock();
        }
    }

    public void close() throws IOException {
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }
}