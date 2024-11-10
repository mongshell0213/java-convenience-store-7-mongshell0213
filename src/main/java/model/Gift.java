package model;

public class Gift {

    private String name;
    private int quantity;

    public Gift(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }
    public int getQuantity(){
        return quantity;
    }
}
