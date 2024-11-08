package model;

import java.util.ArrayList;
import java.util.List;
import model.Order;

public class Orders {
    private List<Order> inputOrders;

    public Orders(){
        inputOrders = new ArrayList<>();
    }

    public void add(Order order){
        inputOrders.add(order);
    }

    public int getSize(){
        return inputOrders.size();
    }
}
