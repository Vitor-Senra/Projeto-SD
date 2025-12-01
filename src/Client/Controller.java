package Client;

import Aux.SharedData;
import Exceptions.*;
import Conn.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;


public class Controller {
    private Demultiplexer m;
    private Socket s;
    private SharedData sharedData;

    Controller() {
        try {
            s = new Socket("localhost", 12345);
            this.m = new Demultiplexer(new Connection(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.start();
        this.sharedData = new SharedData ();
    }

    public void fazerLogin(String username, String password) throws InvalidCredentialsException, IOException {
            m.send(Type.LOGIN, (username + ";" + password).getBytes());
    }

    public void fazerRegisto(String username, String password) throws UsernameAlreadyExistsException, IOException {
        m.send(Type.REGISTER, (username + ";" + password).getBytes());
    }

    public void RegisterEvent(String product, int quantity, double price) {
        new Thread(() -> {
            try  {
                // send request
                m.send(Type.REGISTER_EVENT, (product + ";" + quantity + ";" + price).getBytes());
                int key = sharedData.getCounter();
                sharedData.putData(key,null);
                // get reply
                byte[] data = m.receive(1);
                sharedData.putData(key,new String(data));
            }  catch (Exception ignored) {}
        }).start();
    }

    public void newDay() {
        new Thread(() -> {
            try  {
                // send request
                m.send(Type.NEW_DAY,new byte[0]);
            }  catch (Exception ignored) {}
        }).start();
    }

    public void getSalesQuantity(String product, int day) {
        new Thread(() -> {
            try  {
                // send request
                m.send(Type.GET_SALES_QUANTITY, (product + ";" + day).getBytes());
                int key = sharedData.getCounter();
                // get reply
                byte[] data = m.receive(1);
                sharedData.putData(key,new String(data));
            }  catch (Exception ignored) {}
        }).start();
    }

    public void getSalesVolume(String product, int day) {
        new Thread(() -> {
            try  {
                // send request
                m.send(Type.GET_SALES_VOLUME, (product + ";" + day).getBytes());
                int key = sharedData.getCounter();
                // get reply
                byte[] data = m.receive(1);
                sharedData.putData(key,new String(data));
            }  catch (Exception ignored) {}
        }).start();
    }

    public void getAverageSalesPrice(String product, int day) {
        new Thread(() -> {
            try  {
                // send request
                m.send(Type.GET_AVERAGE_SALES_PRICE, (product + ";" + day).getBytes());
                int key = sharedData.getCounter();
                // get reply
                byte[] data = m.receive(1);
                sharedData.putData(key,new String(data));
            }  catch (Exception ignored) {}
        }).start();
    }

    public void getMaxSalesPrice(String product, int day) {
        new Thread(() -> {
            try  {
                // send request
                m.send(Type.GET_MAX_SALES_PRICE, (product + ";" + day).getBytes());
                int key = sharedData.getCounter();
                // get reply
                byte[] data = m.receive(1);
                sharedData.putData(key,new String(data));
            }  catch (Exception ignored) {}
        }).start();
    }

    public void filterEvents(List<String> product, int day) {
        new Thread(() -> {
            try  {
                // send request
                m.send(Type.GET_SALES_QUANTITY, (product + ";" + day).getBytes());
                int key = sharedData.getCounter();
                // get reply
                byte[] data = m.receive(1);
                sharedData.putData(key,new String(data));
            }  catch (Exception ignored) {}
        }).start();
    }

    public void notifySimultaneousSales(String p1, String p2) {
        new Thread(() -> {
            try  {
                // send request
                m.send(Type.NOTIFY_SIMULTANEOUS_SALES, (p1 + ";" + p2).getBytes());
                int key = sharedData.getCounter();
                // get reply
                byte[] data = m.receive(1);
                sharedData.putData(key,new String(data));
            }  catch (Exception ignored) {}
        }).start();
    }

    public void notifyConsecutiveSales(int n) {
        new Thread(() -> {
            try  {
                // send request
                m.send(Type.NOTIFY_CONSECUTIVE_SALES, (""+ n).getBytes());
                int key = sharedData.getCounter();
                // get reply
                byte[] data = m.receive(1);
                sharedData.putData(key,new String(data));
            }  catch (Exception ignored) {}
        }).start();
    }

    public String getPendingReplies (){
        return sharedData.toString();
    }

    public boolean hasPendingReplies (){
        return sharedData.isEmpty();
    }

    public void close() throws IOException {
        s.close();
    }

}
