package policy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Sale8000PolicyTest {
    @Test
    void 할인금액_테스트(){
        SalePolicy salePolicy = new Sale8000Policy();

        int payPrice = 8000;

        assertThat(salePolicy.salePrice(payPrice)).isEqualTo(8000);
    }
}
