package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class OrdersTest {
    @Test
    void 주문_추가_테스트(){
        Order order = new Order("콜라",10);

        Orders orders = new Orders();
        orders.add(order);

        assertThat(orders.getSize()).isEqualTo(1);
    }
}
