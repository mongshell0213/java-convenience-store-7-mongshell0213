package model;

import java.util.Objects;

public class Product {
    private String name;
    private int price;
    private String promotion;

    public Product(String name, int price, String promotion) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
    }

    public boolean isPromotionProduct(){
        if(promotion == null)
            return false;
        return true;
    }

    public Product getNoneExistNormalProduct(){
        return new Product(this.name,this.price,null);
    }

    public String getName(){
        return this.name;
    }

    public String getPromotion(){
        return this.promotion;
    }

    public int getPrice(){
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if(this==o)
            return true;
        if(!(o instanceof Product))
            return false;
        Product other = (Product)o;
        return Objects.equals(this.name,other.name)
            && this.price == other.price
            && this.promotion == other.promotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,price,promotion);
    }
}
