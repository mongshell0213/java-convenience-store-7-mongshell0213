package model;

import java.util.ArrayList;
import java.util.List;

public class Gifts {

    private List<Gift> gifts;

    public Gifts() {
        gifts = new ArrayList<>();
    }

    public void add(Gift gift) {
        if(gift.getQuantity()!=0)
            gifts.add(gift);
    }

    public List<Gift> get(){
        return this.gifts;
    }
}
