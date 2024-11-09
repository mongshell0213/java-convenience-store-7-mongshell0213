package factory;

import static org.assertj.core.api.Assertions.assertThat;

import constants.Constants;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Promotion;
import model.Promotions;
import org.junit.jupiter.api.Test;

public class PromotionFactoryTest {
    @Test
    void 입력_테스트(){
        Promotions promotions = new Promotions();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",2,1,start,end);

        PromotionFactory.input(promotions);

        assertThat(promotions.isContain(promotion)).isTrue();
    }
}
