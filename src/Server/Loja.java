package Server;

import Exceptions.InvalidCredentialsException;
import Exceptions.UsernameAlreadyExistsException;

import java.util.List;

public interface Loja {

    public void fazerLogin(String username, String password) throws InvalidCredentialsException;
    public void fazerRegisto(String username, String password) throws UsernameAlreadyExistsException;
    public void RegisterEvent(String product, int quantity, double price);
    public void newDay();
    public int getSalesQuantity(String product, int day);
    public int getSalesVolume(String product, int day);
    public double getAverageSalesPrice(String product, int day);
    public double getMaxSalesPrice(String product, int day);
    public void filterEvents(List<String> product, int day); //todo tipo de return
    public boolean notifySimultaneousSales(String p1, String p2);
    public boolean notifyConsecutiveSales(int n);
}
