package repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import model.Production;
import org.junit.jupiter.api.Test;

public class ProductionsTest {
    @Test
    void 상품추가확인1() {
        Production production = new Production("콜라", 1000, 10,"MD추천상품");
        Productions productions = new Productions();

        productions.add(production);

        assertThat(productions.size()).isEqualTo(1);
    }
}
