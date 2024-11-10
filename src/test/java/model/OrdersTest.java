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

    @Test
    void 주문_수정_테스트(){
        Order order = new Order("콜라",10);
        Orders orders = new Orders();
        orders.add(order);

        orders.update(order,11);

        assertThat(orders.getOrders().equals(new Order("콜라",11)));
    }
}
