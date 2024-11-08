package repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ProductFactoryTest {
    @Test
    void 입력_테스트() throws IOException {
        Products products = new Products();

        ProductFactory.input(products);

        assertThat(products.size()).isEqualTo(18);
    }
}
