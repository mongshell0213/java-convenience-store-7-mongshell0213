package model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class ProductsTest {

    @Test
    void 상품추가확인() {
        Product product = new Product("콜라", 1000, "MD추천상품");
        Products products = new Products();

        products.add(product, 10);

        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    void 상품_존재_테스트() {
        Product product = new Product("콜라", 1000, "MD추천상품");
        Products products = new Products();
        products.add(product, 10);

        Order order = new Order("콜라", 5);

        assertThat(products.isExist(order)).isTrue();
    }

    @Test
    void 상품_미존재_테스트() {
        Product product = new Product("콜라", 1000, "MD추천상품");
        Products products = new Products();
        products.add(product, 10);

        Order order = new Order("사이다", 5);

        assertThatThrownBy(() -> products.isExist(order)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 프로모션_이름_가져오기_테스트() {
        Products products = new Products();
        Product promotionProduct = new Product("콜라", 1000, "MD추천상품");
        Product normalProduct = new Product("콜라", 1000, null);
        products.add(promotionProduct, 10);
        products.add(normalProduct, 10);

        Order order = new Order("콜라", 5);

        assertThat(products.getPromotionName(order)).isEqualTo("MD추천상품");
    }

    @Test
    void 프로모션_이름_가져오기2_테스트() {
        Products products = new Products();
        Product normalProduct = new Product("콜라", 1000, null);
        products.add(normalProduct, 10);

        Order order = new Order("콜라", 5);

        assertThat(products.getPromotionName(order)).isNull();
    }

    @Test
    void 상품_과잉구매_테스트() {
        Product product = new Product("콜라", 1000, "MD추천상품");
        Products products = new Products();
        products.add(product, 10);

        Order order = new Order("콜라", 15);

        assertThatThrownBy(() -> products.buy(order, "MD추천상품")).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void 상품_구매_테스트() {
        Product product = new Product("콜라", 1000, null);
        Products products = new Products();
        products.add(product, 10);

        Order order = new Order("콜라", 10);
        products.buy(order, null);

        assertThat(products.getQuantity(product)).isEqualTo(0);
    }


}
