package repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductionsTest {
    @Test
    void 상품추가확인() {
        Productions productions = new Productions();
        String[] info = {"콜라", "1000", "10", "MD추천상품"};

        productions.add(info);

        assertThat(productions.size()).isEqualTo(1);
    }

    @Test
    void 상품추가실패() {
        Productions productions = new Productions();
        String[] info = {"콜라", "dfad", "10", "MD추천상품"};
        
        assertThatThrownBy(()->productions.add(info)).isInstanceOf(NumberFormatException.class);
    }
}
