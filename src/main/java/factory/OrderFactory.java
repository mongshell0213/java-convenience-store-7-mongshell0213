package factory;

import static constants.Constants.BLANK;
import static constants.Constants.DELIMITER;
import static constants.Constants.ORDER_DELIMITER;
import static constants.Constants.ORDER_ENDER;
import static constants.Constants.ORDER_NAME_POSITION;
import static constants.Constants.ORDER_QUANTITY_POSITION;
import static constants.Constants.ORDER_STARTER;

import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.Orders;
import validation.Validation;

public class OrderFactory {

    public static void add(final Orders orders, final String input) {
        validate(input);
        List<Order> inputOrders = splitOrder(input);
        for (Order order : inputOrders) {
            orders.add(order);
        }
    }

    private static void validate(final String string) {
        Validation.blank(string);
        String[] splitOrder = string.split(DELIMITER);
        for (String order : splitOrder) {
            Validation.orderPattern(order);
        }
    }

    private static List<Order> splitOrder(final String string) {
        String[] splitOrder = string.split(DELIMITER);
        List<Order> orders = new ArrayList<>();
        for (String order : splitOrder) {
            order = order.replace(ORDER_STARTER, BLANK).replace(ORDER_ENDER, BLANK);
            String name = order.split(ORDER_DELIMITER)[ORDER_NAME_POSITION];
            int quantity = Integer.parseInt(order.split(ORDER_DELIMITER)[ORDER_QUANTITY_POSITION]);
            orders.add(new Order(name, quantity));
        }
        return orders;
    }
}
