package model;

import java.time.LocalDate;
import java.util.Objects;

public class Promotion {

    private String name;
    private int buy;
    private int get;
    private LocalDate start_date;
    private LocalDate end_date;

    public Promotion(final String name, final int buy, final int get
        , final LocalDate start_date, final LocalDate end_date) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public boolean isStartEqualOrAfter(final LocalDate date) {
        return (date.isEqual(start_date) || date.isAfter(start_date));
    }

    public boolean isEndBeforeOrEqual(final LocalDate date) {
        return (date.isBefore(end_date) || date.isEqual(end_date));
    }

    public boolean isSame(final String promotionName) {
        return this.name.equals(promotionName);
    }

    public String getName() {
        return this.name;
    }

    public boolean possibleMoreFree(final int quantity) {
        return calcRemainingQuantity(quantity) == buy;
    }

    public int calcRemainingQuantity(final int quantity) {
        int quotient = quantity / (buy + get);
        return quantity - quotient * (buy + get);
    }


    public int getBuyCount(final int quantity) {
        int quotient = quantity / (buy + get);
        return quotient * buy + (quantity - quotient * (buy + get));
    }

    public int getFreeCount(final int quantity) {
        return quantity - getBuyCount(quantity);
    }

    public int getMaxValidPromotion(final int quantity) {
        int quotient = quantity / (buy + get);
        return quotient * (buy + get);
    }

    public int notApplyPromotionCount(final int quantity) {
        int quotient = quantity / (buy + get);
        return quantity - quotient * (buy + get);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Promotion)) {
            return false;
        }
        Promotion other = (Promotion) obj;
        return compare(other);
    }

    private boolean compare(final Promotion other) {
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
