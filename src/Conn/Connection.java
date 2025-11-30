package Conn;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Connection implements AutoCloseable {
    public static class Frame {
        public final int tag;
        public final byte[] data;
        public Frame(int tag, byte[] data) { this.tag = tag; this.data = data; }
    }
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

    public void send(Frame frame) throws IOException {
        send(frame.tag, frame.data);
    }

    public void send(int tag, byte[] data) throws IOException {
        writeLock.lock();
        try{
            if (tag!= 0){
                out.writeInt(tag);
                out.writeInt(data.length);
                out.write(data);
                out.flush();
            }
        }   finally{
            writeLock.unlock();
        }
    }

    public Frame receive() throws IOException {
        readLock.lock();
        try{
            int tag = in.readInt();
            int length = in.readInt();
            byte[] data = new byte[length];
            in.readFully(data);
            return new Frame(tag, data);
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