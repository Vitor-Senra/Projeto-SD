package Server;

import java.util.List;

import Aux.*;
import Conn.Connection;
import Exceptions.*;

public class LojaSkeleton implements Skeleton {

    private Loja loja;

    public LojaSkeleton(Loja loja) {
        this.loja = loja;
    }

    @Override
    public void Handle(Message msg, Connection conn) throws Exception {
        System.out.println("LojaSkeleton: Método recebido.");
        int methodId = msg.getId();
        switch (msg.getType().getValue()) {
            case 1: // fazerLogin
                AuthMessage authMsg = (AuthMessage) msg;
                String username = authMsg.getUsername();
                String password = authMsg.getPassword();
                Message res = null;
                try {
                    loja.fazerLogin(username, password);
                    res = new ReplyBooleanMessage(methodId, 0, true);

                } catch (InvalidCredentialsException e) {
                    res = new ReplyBooleanMessage(methodId, 0, false);
                    break;
                }
                conn.send(res);
                break;
            case 2: // fazerRegisto
                authMsg = (AuthMessage) msg;
                username = authMsg.getUsername();
                password = authMsg.getPassword();
                try {
                    loja.fazerRegisto(username, password);
                    res = new ReplyBooleanMessage(methodId, 0, true);
                } catch (UsernameAlreadyExistsException e) {
                    res = new ReplyBooleanMessage(methodId, 0, false);
                }
                conn.send(res);
                break;
            case 3: // RegisterEvent
                RegisterEventMessage regMsg = (RegisterEventMessage) msg;
                String product = regMsg.getProduct();
                int quantity = regMsg.getQuantity();
                double price = regMsg.getPrice();
                loja.RegisterEvent(product, quantity, price);
                res = new ReplyBooleanMessage(methodId, 0, true);
                conn.send(res);
                break;
            case 4: // newDay
                loja.newDay();
                res = new ReplyBooleanMessage(methodId, 0, true);
                conn.send(res);
                break;
            case 5: // getSalesQuantity
                AggregationMessage aggMsg = (AggregationMessage) msg;
                String productName = aggMsg.getProductName();
                int day = aggMsg.getDay();
                int salesQuantity = loja.getSalesQuantity(productName, day);
                res = new ReplyIntMessage(methodId, 0, salesQuantity);
                conn.send(res);
                break;
            case 6: // getSalesVolume
                aggMsg = (AggregationMessage) msg;
                productName = aggMsg.getProductName();
                day = aggMsg.getDay();
                int salesVolume = loja.getSalesVolume(productName, day);
                res = new ReplyIntMessage(methodId, 0, salesVolume);
                conn.send(res);
                break;
            case 7: // getAverageSalesPrice
                aggMsg = (AggregationMessage) msg;
                productName = aggMsg.getProductName();
                day = aggMsg.getDay();
                double averagePrice = loja.getAverageSalesPrice(productName, day);
                res = new ReplyDoubleMessage(methodId, 0, averagePrice);
                conn.send(res);
                break;
            case 8: // getMaxSalesPrice
                aggMsg = (AggregationMessage) msg;
                productName = aggMsg.getProductName();
                day = aggMsg.getDay();
                double maxPrice = loja.getMaxSalesPrice(productName, day);
                res = new ReplyDoubleMessage(methodId, 0, maxPrice);
                conn.send(res);
                break;
            case 9: // filterEvents
                FilterMessage filterMsg = (FilterMessage) msg;
                List<String> products = filterMsg.getProducts();
                day = filterMsg.getDay();
                loja.filterEvents(products, day);
                res = new ReplyBooleanMessage(methodId, 0, true);
                conn.send(res);
                break;
            case 10: // notifySimultaneousSales
                SimultaneousSaleMessage simMsg = (SimultaneousSaleMessage) msg;
                String p1 = simMsg.getProduct1();
                String p2 = simMsg.getProduct2();
                boolean result = loja.notifySimultaneousSales(p1, p2);
                res = new ReplyBooleanMessage(methodId, 0, result);
                conn.send(res);
                break;
            case 11: // notifyConsecutiveSales
                ConsecutiveSaleMessage consMsg = (ConsecutiveSaleMessage) msg;
                int n = consMsg.getNVendas();
                result = loja.notifyConsecutiveSales(n);
                res = new ReplyBooleanMessage(methodId, 0, result);
                conn.send(res);
                break;
            default:
                System.out.println("Método inválido recebido na LojaSkeleton.");
                throw new IllegalArgumentException("Invalid method ID");
        }
    }
}
