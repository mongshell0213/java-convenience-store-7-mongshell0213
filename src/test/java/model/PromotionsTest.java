package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import constants.Constants;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PromotionsTest {

    @BeforeEach
    void init(){
        Promotions promotions = new Promotions();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",2,1,start,end);

        promotions.add(promotion);

    }
    @Test
    void 프로모션_추가_테스트(){
        Promotions promotions = new Promotions();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",2,1,start,end);

        promotions.add(promotion);

        assertThat(promotions.isContain(promotion)).isTrue();
    }

    @Test
    void 프로모션_날짜_테스트(){
        Promotions promotions = new Promotions();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",2,1,start,end);
        promotions.add(promotion);

        String validDateString = "2024-11-09";
        LocalDate validDate = LocalDate.parse(validDateString,dateTimeFormatter);
        String invalidDateString = "2025-11-09";
        LocalDate invalidDate = LocalDate.parse(invalidDateString,dateTimeFormatter);

        assertThat(promotions.isPromotion(validDate,"탄산2+1")).isTrue();
        assertThat(promotions.isPromotion(validDate,"MD추천상품")).isFalse();
        assertThat(promotions.isPromotion(invalidDate,"탄산2+1")).isFalse();
        assertThat(promotions.isPromotion(validDate,null)).isFalse();
    }

}