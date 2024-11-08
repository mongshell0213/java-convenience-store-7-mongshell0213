package model;

public class Production {
    private String name;
    private int price;
    private int amount;
    private String promotion;

    public Production(String name, int price, int amount, String promotion) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.promotion = promotion;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        Production other = (Production)o;
        return this.name == other.name
            && this.price == other.price
            && this.promotion == other.promotion;
    }
}
