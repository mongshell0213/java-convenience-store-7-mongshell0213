package model;

import java.util.Objects;

public class Production {
    private String name;
    private int price;
    private String promotion;

    public Production(String name, int price, String promotion) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
    }

    public boolean isPromotionProduct(){
        if(promotion == null)
            return false;
        return true;
    }

    public Production getNoneExistNormalProduct(){
        return new Production(this.name,this.price,null);
    }

    @Override
    public boolean equals(Object o) {
        if(this==o)
            return true;
        if(!(o instanceof Production))
            return false;
        Production other = (Production)o;
        return Objects.equals(this.name,other.name)
            && this.price == other.price
            && this.promotion == other.promotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,price,promotion);
    }
}
