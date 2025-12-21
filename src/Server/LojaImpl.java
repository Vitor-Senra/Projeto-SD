package Server;

import Exceptions.InvalidCredentialsException;
import Exceptions.UsernameAlreadyExistsException;

import java.util.List;

public class LojaImpl implements Loja {

    public void fazerLogin(String username, String password) throws InvalidCredentialsException{
        // todo
    }

    public void fazerRegisto(String username, String password) throws UsernameAlreadyExistsException{
        // todo
    }

    public void RegisterEvent(String product, int quantity, double price){
        // todo
    }

    public void newDay(){
        // todo
    }

    public int getSalesQuantity(String product, int day){
        // todo
        return 0;
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
