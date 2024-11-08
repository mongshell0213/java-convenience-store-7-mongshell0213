package repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import model.Production;
import org.junit.jupiter.api.Test;

public class ProductionsTest {
    @Test
    void 상품추가확인() {
        Production production = new Production("콜라", 1000,"MD추천상품");
        Productions productions = new Productions();

        productions.add(production,10);

        assertThat(productions.size()).isEqualTo(1);
    }

    @Test
    void 상품_과잉구매_테스트(){
        Production production = new Production("콜라", 1000,"MD추천상품");
        Productions productions = new Productions();
        productions.add(production,10);

        Production buyProduction = new Production("콜라", 1000,"MD추천상품");

        assertThatThrownBy(()->productions.buy(buyProduction,15)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_미존재_테스트(){
        Production production = new Production("콜라", 1000,"MD추천상품");
        Productions productions = new Productions();
        productions.add(production,10);

        Production buyProduction = new Production("사이다", 1000,"MD추천상품");

        assertThatThrownBy(()->productions.buy(buyProduction,10)).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void 상품_구매_테스트(){
        Production production = new Production("콜라", 1000,"MD추천상품");
        Productions productions = new Productions();
        productions.add(production,10);

        Production buyProduction = new Production("콜라", 1000,"MD추천상품");
        productions.buy(buyProduction,5);

        assertThat(productions.getAmount(production)).isEqualTo(5);
    }
}
