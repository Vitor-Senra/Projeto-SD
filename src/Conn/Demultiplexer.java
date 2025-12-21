package Conn;

import Aux.Message;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Demultiplexer implements AutoCloseable {
    private class Entry {
        public ReentrantLock lock = new ReentrantLock();
        public Condition cond = lock.newCondition();
        private Deque<Message> queue = new ArrayDeque<>();
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
                    Message msg = conn.receive();
                    int tag = msg.getId();
                    l.lock();
                    try {
                        Entry entry = getEntry(tag);
                        entry.lock.lock();
                        try {
                            entry.queue.addLast(msg);
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

    public void send(Message mensagem) throws IOException {
        this.conn.send(mensagem);
    }

    public Message receive(int tag) throws IOException, InterruptedException {
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