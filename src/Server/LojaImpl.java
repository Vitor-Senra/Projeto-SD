package Server;

import Exceptions.InvalidCredentialsException;
import Exceptions.UsernameAlreadyExistsException;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class LojaImpl implements Loja {

    private static class Event {
        String product;
        int quantity;
        double price;

        Event(String product, int quantity, double price) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
        }
    }

    private Map<String, String> users = new HashMap<>(); // username -> password
    private Map<Integer, List<Event>> eventsByDay = new HashMap<>(); // day -> list of events
    private Map<String, Map<Integer, List<Event>>> seriesByProduct = new HashMap<>(); // product -> (day -> events)
    private int currentDay = 0;
    private ReentrantLock lock = new ReentrantLock();

    private static final int MAX_SERIES_IN_MEMORY = 10;
    private int D = 30; 

    public void fazerLogin(String username, String password) throws InvalidCredentialsException{
        lock.lock();
        try {
            if (!users.containsKey(username)) {
                throw new InvalidCredentialsException("Utilizador não existe");
            }
            String storedPassword = users.get(username);
            if (!storedPassword.equals(password)) {
                throw new InvalidCredentialsException("Palavra-passe incorreta");
            }
        } finally {
            lock.unlock();
        }
    }

    public void fazerRegisto(String username, String password) throws UsernameAlreadyExistsException {
        lock.lock();
        try {
            if (users.containsKey(username)) {
                throw new UsernameAlreadyExistsException("Utilizador já existe");
            }
            users.put(username, password);
        } finally {
            lock.unlock();
        }
    }

    public void RegisterEvent(String product, int quantity, double price) {
        lock.lock();
        try {
            Event event = new Event(product, quantity, price);

            eventsByDay.computeIfAbsent(currentDay, k -> new ArrayList<>()).add(event);

            seriesByProduct.computeIfAbsent(product, k -> new HashMap<>())
                    .computeIfAbsent(currentDay, k -> new ArrayList<>())
                    .add(event);
        } finally {
            lock.unlock();
        }
    }

    public void newDay() {
        lock.lock();
        try {
            currentDay++;

            int minDay = Math.max(0, currentDay - D);
            eventsByDay.entrySet().removeIf(entry -> entry.getKey() < minDay);
            
            for (Map<Integer, List<Event>> series : seriesByProduct.values()) {
                series.entrySet().removeIf(entry -> entry.getKey() < minDay);
            }
        } finally {
            lock.unlock();
        }
    }

    public int getSalesQuantity(String product, int day) {
        lock.lock();
        try {
            // Validar que o dia está dentro do intervalo válido
            if (day < 0 || day > currentDay) {
                return 0;
            }

            Map<Integer, List<Event>> series = seriesByProduct.get(product);
            if (series == null) {
                return 0;
            }

            List<Event> events = series.get(day);
            if (events == null) {
                return 0;
            }

            int total = 0;
            for (Event event : events) {
                total += event.quantity;
            }
            return total;
        } finally {
            lock.unlock();
        }
    }

    public int getSalesVolume(String product, int day){
        // todo
        return 0;
    }

    public double getAverageSalesPrice(String product, int day){
        // todo
        return 0.0;
    }

    public double getMaxSalesPrice(String product, int day) {
        // todo
        return 0.0;
    }

    public void filterEvents(List<String> product, int day){
        // todo
    }
    public boolean notifySimultaneousSales(String p1, String p2){
        // todo
        return false;
    }

    public boolean notifyConsecutiveSales(int n){
        // todo
        return false;
    }
}
