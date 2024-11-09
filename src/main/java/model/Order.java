package model;

public class Order {
    private String name;
    private int quantity;
    public Order(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }
    public String getName(){
        return this.name;
    }
    public int getQuantity(){
        return this.quantity;
    }
}
