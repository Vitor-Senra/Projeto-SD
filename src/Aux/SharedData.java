package Aux;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedData {
    private Map<Integer, String> dataMap;
    private int counter = 1;
    private Lock lock;

    public SharedData() {
        dataMap = new java.util.HashMap<>();
        lock = new ReentrantLock();
    }

    public int getCounter() {
        lock.lock();
        try {
            return counter++;
        } finally {
            lock.unlock();
        }
    }

    public void putData(int key, String value) {
        lock.lock();
        try {
            dataMap.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    public Boolean isEmpty() {
        lock.lock();
        try {
            return dataMap.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        lock.lock();
        try {
            for (Map.Entry<Integer, String> entry : dataMap.entrySet()) {
                if (entry.getValue() != null)
                    sb.append("Key: ").append(entry.getKey()).append(", Value: ").append(entry.getValue()).append("\n");
                else
                    sb.append("Key: ").append(entry.getKey()).append(", Value: Ainda sem resposta do servidor\n");
            }
            return sb.toString();
        } finally {
            lock.unlock();
        }
    }
}
