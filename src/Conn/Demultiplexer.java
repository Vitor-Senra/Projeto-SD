package Conn;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


////  todo mudar para funcionar no novo tipo de comunicação
public class Demultiplexer implements AutoCloseable {
    private class Entry {
        public ReentrantLock lock = new ReentrantLock();
        public Condition cond = lock.newCondition();
        private Deque<byte[]> queue = new ArrayDeque<>();
        private Exception exception = null;
    }

    private Connection conn;
    private Map<Integer, Entry> entries = new HashMap<>();
    private ReentrantLock l = new ReentrantLock();

    public Demultiplexer(Connection conn) {
        this.conn = conn;
    }

    private Entry getEntry(int tag) {
        l.lock();
        try {
            if (!entries.containsKey(tag)) {
                Entry entry = new Entry();
                entries.put(tag, entry);
            }
            return entries.get(tag);
        } finally {
            l.unlock();
        }
    }

    public void start() {
        Thread bg = new Thread(() -> {
            try {
                for (;;) {
                    Frame frame = conn.receive();
                    int tag = frame.getType().getValue();
                    byte[] data = frame.getData();
                    l.lock();
                    try {
                        Entry entry = getEntry(tag);
                        entry.lock.lock();
                        try {
                            entry.queue.addLast(data);
                            entry.cond.signal();
                        } finally {
                            entry.lock.unlock();
                        }
                    } finally {
                        l.unlock();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                l.lock();
                try {
                    for (Entry entry : entries.values()) {
                        entry.exception = e;
                        entry.cond.signal();
                    }
                } finally {
                    l.unlock();
                }
            }
        });
        bg.start();
    }

    public void send(Frame frame) throws IOException {
        this.conn.send(frame);
    }

    public void send(Type type, byte[] data) throws IOException {
        this.conn.send(type, data);
    }

    public byte[] receive(int tag) throws IOException, InterruptedException {
        Entry entry = getEntry(tag);
        entry.lock.lock();
        try {
            while (entry.queue.isEmpty() && entry.exception == null) {
                entry.cond.await();
            }
            if (entry.exception != null) {
                throw new IOException("Exception in background thread", entry.exception);
            }
            return entry.queue.removeFirst();
        } finally {
            if (entry.queue.isEmpty()) {
                l.lock();
                try {
                    entries.remove(tag);
                } finally {
                    l.unlock();
                }
            }
            entry.lock.unlock();
        }
    }

    public void close() throws IOException {
        this.conn.close();
    }
}