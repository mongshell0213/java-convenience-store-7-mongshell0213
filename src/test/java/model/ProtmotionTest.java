package model;

import static org.assertj.core.api.Assertions.assertThat;

import constants.Constants;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

public class ProtmotionTest {

    @Test
    void 추가구매_테스트() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString, dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString, dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1", 2, 1, start, end);

        int moreBuyCount = promotion.getMoreBuy(4);

        assertThat(moreBuyCount).isEqualTo(1);
    }
}
