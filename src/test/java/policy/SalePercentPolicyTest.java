package policy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class SalePercentPolicyTest {
    @Test
    void 할인_금액_테스트(){
        SalePolicy salePolicy = new SalePercentPolicy();

        int payPrice = 8000;

        assertThat(salePolicy.salePrice(payPrice)).isEqualTo(8000 / 100 * 30);
    }
}
