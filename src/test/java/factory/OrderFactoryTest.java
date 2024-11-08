package factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.Orders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderFactoryTest {

    @Test
    void 주문_입력_성공_테스트() {
        String input = "[사이다-2],[감자칩-1]";
        Orders orders = new Orders();

        OrderFactory.add(orders, input);

        assertThat(orders.getSize()).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"사이다-2", "사이다", "[사이다-2", "사이다-2]", "[사이다-a]", "[사이다]"})
    void 주문_실패_테스트(String input) {
        Orders orders = new Orders();

        assertThatThrownBy(() -> OrderFactory.add(orders, input))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
