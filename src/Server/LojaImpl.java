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

    public void getSalesQuantity(String product, int day){
        // todo
    }

    public void getSalesVolume(String product, int day){
        // todo
    }

    public void getAverageSalesPrice(String product, int day){
        // todo
    }

    public void getMaxSalesPrice(String product, int day) {
        // todo
    }

    public void filterEvents(List<String> product, int day){
        // todo
    }
    public void notifySimultaneousSales(String p1, String p2){
        // todo
    }

    public void notifyConsecutiveSales(int n){
        // todo
    }
}
