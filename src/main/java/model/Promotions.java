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
    public int getSize(){
        return this.inputPromotions.size();
    }
    public boolean isContain(Promotion promotion){
        return inputPromotions.stream().filter(p->p.equals(promotion)).findAny().isPresent();
    }
}
