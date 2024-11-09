package factory;

import constants.Constants;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.Orders;
import validation.Validation;

public class OrderFactory {

    private static final int NAME_POSITION = 0;
    private static final int QUANTITY_POSITION = 1;

    public static void add(Orders orders, String input) {
        validate(input);
        List<Order> inputOrders = splitOrder(input);
        for (Order order : inputOrders) {
            orders.add(order);
        }
    }

    private static void validate(String string) {
        Validation.blank(string);
        String[] splitOrder = string.split(Constants.DELIMITER);
        for (String order : splitOrder) {
            Validation.orderPattern(order);
        }
    }

    private static List<Order> splitOrder(String string) {
        String[] splitOrder = string.split(Constants.DELIMITER);
        List<Order> orders = new ArrayList<>();
        for (String order : splitOrder) {
            order = order.replace(Constants.ORDER_STARTER, Constants.BLANK)
                .replace(Constants.ORDER_ENDER, Constants.BLANK);
            String name = order.split(Constants.ORDER_DELIMITER)[NAME_POSITION];
            int quantity = Integer.parseInt(order.split(Constants.ORDER_DELIMITER)[QUANTITY_POSITION]);
            orders.add(new Order(name, quantity));
        }
        return orders;
    }
}
