package repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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

    @Test
    void 상품_과잉구매_테스트(){
        Production production = new Production("콜라", 1000, 10,"MD추천상품");
        Productions productions = new Productions();
        productions.add(production);

        Production buyProduction = new Production("콜라", 1000, 15,"MD추천상품");

        assertThatThrownBy(()->productions.overBuy(buyProduction)).isInstanceOf(IllegalArgumentException.class);
    }
}
