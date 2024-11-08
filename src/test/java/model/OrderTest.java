package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class OrderTest {
    @Test
    void 주문_생성_테스트(){
        String name = "콜라";
        int quantity = 5;

        Order order = new Order(name,quantity);

        assertThat(order).isInstanceOf(Order.class);
    }
}
