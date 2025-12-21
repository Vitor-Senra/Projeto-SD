package Client;

import Aux.*;
import Aux.SharedData;
import Exceptions.*;
import Conn.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import Server.Loja;


public class LojaStub implements Loja {
    private Demultiplexer m;
    private Socket s;
    private SharedData sharedData;

    LojaStub() throws Exception {
        s = new Socket("localhost", 12345);
        this.m = new Demultiplexer(new Connection(s));
        m.start();
        this.sharedData = new SharedData ();
    }

    public void fazerLogin(String username, String password) throws InvalidCredentialsException {
        int key = sharedData.getCounter();
        try {
            m.send(new AuthMessage(key,0,Type.LOGIN,username,password));
        } catch (IOException exceptionIgnored) {
        }
    }


    public void fazerRegisto(String username, String password) throws UsernameAlreadyExistsException {
        int key = sharedData.getCounter();
        try{
            m.send(new AuthMessage(key,0,Type.REGISTER,username,password));
            m.receive(key);
        } catch (Exception exceptionIgnored) {}
    }

    public void RegisterEvent(String product, int quantity, double price) {
        int key = sharedData.getCounter();
        try  {
            // send request
            sharedData.putData(key,null);
            m.send(new RegisterEventMessage(key,0,Type.REGISTER_EVENT, product,quantity,price));
            // get reply
            Message data = m.receive(key);
            sharedData.putData(key,data.toString());
        }  catch (Exception e) {
            sharedData.putData(key,"Erro ao conectar ao servidor.");
        }
    }

    public void newDay() {
        int key = sharedData.getCounter();
        try  {
            // send request
            m.send(new EmptyMessage(key,0,Type.REGISTER_EVENT));
        }  catch (Exception e) {
            sharedData.putData(key,"Erro ao conectar ao servidor.");
        }
    }

    public int getSalesQuantity(String product, int day) {
        int key = sharedData.getCounter();
        try  {
            // send request
            m.send(new AggregationMessage(key,0,Type.REGISTER_EVENT, product,day));
            // get reply
            Message data = m.receive(key);
            sharedData.putData(key,data.toString());
            ReplyIntMessage res = (ReplyIntMessage) data;
            return res.getValue();
        }  catch (Exception e) {
            sharedData.putData(key,"Erro ao conectar ao servidor.");
        }
        return -1;
    }

    public int getSalesVolume(String product, int day) {
        int key = sharedData.getCounter();
        try  {
            // send request
            m.send(new AggregationMessage(key,0,Type.REGISTER_EVENT, product,day));
            // get reply
            Message data = m.receive(key);
            sharedData.putData(key,data.toString());
            ReplyIntMessage res = (ReplyIntMessage) data;
            return res.getValue();
        }  catch (Exception e) {
            sharedData.putData(key,"Erro ao conectar ao servidor.");
        }
        return -1;
    }

    public double getAverageSalesPrice(String product, int day) {
        int key = sharedData.getCounter();
        try  {
            // send request
            m.send(new AggregationMessage(key,0,Type.REGISTER_EVENT, product,day));
            // get reply
            Message data = m.receive(key);
            sharedData.putData(key,data.toString());
            ReplyDoubleMessage res = (ReplyDoubleMessage) data;
            return res.getValue();
        }  catch (Exception e) {
            sharedData.putData(key,"Erro ao conectar ao servidor.");
        }
        return -1;
    }

    public double getMaxSalesPrice(String product, int day) {
        int key = sharedData.getCounter();
        try  {
            // send request
            m.send(new AggregationMessage(key,0,Type.REGISTER_EVENT, product,day));
            // get reply
            Message data = m.receive(key);
            sharedData.putData(key,data.toString());
            ReplyDoubleMessage res = (ReplyDoubleMessage) data;
            return res.getValue();
        }  catch (Exception e) {
            sharedData.putData(key,"Erro ao conectar ao servidor.");
        }
        return -1;
    }

    public void filterEvents(List<String> product, int day) {
        int key = sharedData.getCounter();
        try  {
            // send request
            m.send(new FilterMessage(key,0,Type.REGISTER_EVENT, product,day));
            // get reply
            Message data = m.receive(key);
            sharedData.putData(key,data.toString());
        }  catch (Exception e) {
            sharedData.putData(key,"Erro ao conectar ao servidor.");
        }
    }

    public boolean notifySimultaneousSales(String p1, String p2) {
        int key = sharedData.getCounter();
        try  {
            // send request
            m.send(new SimultaneousSaleMessage(key,0,Type.REGISTER_EVENT, p1,p2));
            // get reply
            Message data = m.receive(key);
            sharedData.putData(key,data.toString());
            ReplyBooleanMessage res = (ReplyBooleanMessage) data;
            return res.getSuccess();
        }  catch (Exception e) {
            sharedData.putData(key,"Erro ao conectar ao servidor.");
        }
        return false;
    }

    public boolean notifyConsecutiveSales(int n) {
        int key = sharedData.getCounter();
        try  {
            // send request
            m.send(new ConsecutiveSaleMessage(key,0,Type.REGISTER_EVENT, n));
            // get reply
            Message data = m.receive(key);
            sharedData.putData(key,data.toString());
            ReplyBooleanMessage res = (ReplyBooleanMessage) data;
            return res.getSuccess();
        }  catch (Exception e) {
            sharedData.putData(key,"Erro ao conectar ao servidor.");
        }
        return false;
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
