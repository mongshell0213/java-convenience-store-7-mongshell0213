package model;

import java.util.ArrayList;
import java.util.List;

public class Promotions {
    private List<Promotion> inputPromotions;
    public Promotions(){
        inputPromotions = new ArrayList<>();
    }
    public void add(Promotion promotion){
        inputPromotions.add(promotion);
    }
}
