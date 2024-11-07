package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings={"탄산2+1"})
    void 아이템_생성_테스트(String promotion){
        Product product = new Product("콜라",1000,10,promotion);

        assertThat(product).isInstanceOf(Product.class);
    }
}
