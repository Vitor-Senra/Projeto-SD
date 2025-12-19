package Server;

import Exceptions.InvalidCredentialsException;
import Exceptions.UsernameAlreadyExistsException;

import java.io.IOException;
import java.util.List;

public interface Loja {

    public void fazerLogin(String username, String password) throws InvalidCredentialsException;
    public void fazerRegisto(String username, String password) throws UsernameAlreadyExistsException;
    public void RegisterEvent(String product, int quantity, double price);
    public void newDay();
    public void getSalesQuantity(String product, int day);
    public void getSalesVolume(String product, int day);
    public void getAverageSalesPrice(String product, int day);
    public void getMaxSalesPrice(String product, int day);
    public void filterEvents(List<String> product, int day);
    public void notifySimultaneousSales(String p1, String p2);
    public void notifyConsecutiveSales(int n);
}
