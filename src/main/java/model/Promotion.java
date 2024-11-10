package model;

import java.time.LocalDate;
import java.util.Objects;

public class Promotion {

    private String name;
    private int buy;
    private int get;
    private LocalDate start_date;
    private LocalDate end_date;

    public Promotion(String name, int buy, int get, LocalDate start_date, LocalDate end_date) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public boolean isStartEqualOrAfter(LocalDate date) {
        return (date.isEqual(start_date) || date.isAfter(start_date));
    }

    public boolean isEndBeforeOrEqual(LocalDate date) {
        return (date.isBefore(end_date) || date.isEqual(end_date));
    }

    public boolean isSame(String promotionName) {
        return this.name.equals(promotionName);
    }

    public String getName() {
        return this.name;
    }



    public boolean possibleMoreFree(int quantity) {
        return calcRemainingQuantity(quantity) == buy;
    }

    public int calcRemainingQuantity(int quantity){
        int quotient = quantity/(buy+get);
        return quantity - quotient *(buy+get);
    }

    public int getBuyCount(int quantity) {
        int quotient = quantity / (buy + get);
        return quotient * buy + (quantity - quotient * (buy + get));
    }

    public int getFreeCount(int quantity) {
        return quantity - getBuyCount(quantity);
    }

    public boolean isSameBuy(int buy){
        return this.buy == buy;
    }

    public int getMax(int quantity){
        int quotient = quantity/(buy+get);
        return quotient * buy;
    }

    public boolean isNotApplyPromotion(int quantity){
        return quantity<get+buy;
    }

    public int notApplyPromotionCount(int quantity){
        if(isNotApplyPromotion(quantity))
            return quantity;
        int quotient = quantity / (buy+get);
        return quantity - quotient * (buy+get);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Promotion)) {
            return false;
        }
        Promotion other = (Promotion) obj;
        return compare(other);
    }

    private boolean compare(Promotion other) {
        return Objects.equals(this.name, other.name)
            && this.buy == other.buy
            && this.get == other.get
            && this.start_date.equals(other.start_date)
            && this.end_date.equals(other.end_date);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, buy, get, start_date, end_date);
    }
}
