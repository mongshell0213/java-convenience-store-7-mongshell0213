package model;

import static org.assertj.core.api.Assertions.assertThat;

import constants.Constants;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProtmotionTest {

    DateTimeFormatter dateTimeFormatter;
    String startString;
    String endString;
    LocalDate start;
    LocalDate end;
    Promotion promotion;

    @BeforeEach
    void init() {
        dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        startString = "2024-01-01";
        endString = "2024-12-31";
        start = LocalDate.parse(startString, dateTimeFormatter);
        end = LocalDate.parse(endString, dateTimeFormatter);
        promotion = new Promotion("탄산2+1", 2, 1, start, end);
    }

    @Test
    void 추가구매_테스트() {
        boolean getFree = promotion.getMoreFree(5);

        assertThat(getFree).isTrue();
    }

    @Test
    void 구매수량1_테스트() {
        int buyCount = promotion.getBuyCount(7);

        assertThat(buyCount).isEqualTo(5);
    }

    @Test
    void 구매수량2_테스트() {
        int buyCount = promotion.getBuyCount(6);

        assertThat(buyCount).isEqualTo(4);
    }

    @Test
    void 프로모션_수량_테스트() {
        int buyCount = promotion.getFreeCount(7);

        assertThat(buyCount).isEqualTo(2);
    }
}
