package model;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Product(
        final String name,
        final int price,
        final int quantity,
        final String promotion){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }
}
