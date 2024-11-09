package model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class ProductsTest {
    @Test
    void 상품추가확인() {
        Product product = new Product("콜라", 1000,"MD추천상품");
        Products products = new Products();

        products.add(product,10);

        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    void 상품_존재_테스트(){
        Product product = new Product("콜라", 1000,"MD추천상품");
        Products products = new Products();
        products.add(product,10);

        Order order = new Order("콜라",5);

        assertThat(products.isExist(order)).isTrue();
    }

    @Test
    void 상품_미존재_테스트(){
        Product product = new Product("콜라", 1000,"MD추천상품");
        Products products = new Products();
        products.add(product,10);

        Order order = new Order("사이다",5);

        assertThatThrownBy(()->products.isExist(order)).isInstanceOf(IllegalArgumentException.class);
    }
/*
    @Test
    void 상품_과잉구매_테스트(){
        Product product = new Product("콜라", 1000,"MD추천상품");
        Products products = new Products();
        products.add(product,10);

        Product buyProduct = new Product("콜라", 1000,"MD추천상품");

        assertThatThrownBy(()-> products.buy(buyProduct,15)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void 상품_구매_테스트(){
        Product product = new Product("콜라", 1000,"MD추천상품");
        Products products = new Products();
        products.add(product,10);

        Product buyProduct = new Product("콜라", 1000,"MD추천상품");
        products.buy(buyProduct,5);

        assertThat(products.getQuantity(product)).isEqualTo(5);
    }

 */
}
