package model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
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

    public boolean isPromotion(LocalDate date,String orderPromotionName){
        for(Promotion promotion : inputPromotions){
            if(promotion.isStartEqualOrAfter(date)
                && promotion.isEndBeforeOrEqual(date)
                && promotion.isSame(orderPromotionName))
                return true;
        }
        return false;
    }
}
