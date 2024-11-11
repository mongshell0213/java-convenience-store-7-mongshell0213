package model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Promotions {
    private List<Promotion> inputPromotions;
    public Promotions(){
        inputPromotions = new ArrayList<>();
    }
    public void add(final Promotion promotion){
        inputPromotions.add(promotion);
    }
    public boolean isContain(final Promotion promotion){
        return inputPromotions.stream().filter(p->p.equals(promotion)).findAny().isPresent();
    }

    public Optional<Promotion> isPromotion(final LocalDate date
        ,final String orderPromotionName){
        for(Promotion promotion : inputPromotions){
            if(promotion.isStartEqualOrAfter(date)
                && promotion.isEndBeforeOrEqual(date)
                && promotion.isSame(orderPromotionName))
                return Optional.of(promotion);
        }
        return Optional.empty();
    }
}
