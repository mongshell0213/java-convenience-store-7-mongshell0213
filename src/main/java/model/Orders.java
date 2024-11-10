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

    public List<Order> getOrders(){
        return inputOrders;
    }

    public void update(Order order,int newQuantity){
        int position = inputOrders.indexOf(order);
        Order newOrder = new Order(order.getName(),newQuantity);
        inputOrders.set(position,newOrder);
    }
}
