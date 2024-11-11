package view;

import constants.Constants;
import java.util.List;
import model.Gift;
import model.Gifts;
import model.Order;
import model.Orders;
import model.Product;
import model.Products;

import static constants.Constants.BLANK;
import static constants.Constants.NOT_EXIST_VALUE;
import static java.lang.System.lineSeparator;

public class OutputView {

    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다."
        + lineSeparator() + "현재 보유하고 있는 상품입니다." + lineSeparator();

    private static final String PRODUCTION_FORMAT_MESSAGE = "- %S %,d원 %s %s";
    private static final String NONE_PRODUCTION_MESSAGE = "재고 없음";
    private static final String COUNT_UNIT = "개";

    private static final String RECEIPT_MESSAGE = "==============W 편의점================";
    private static final String ORDER_MESSAGE = "상품명\t\t수량\t금액";
    private static final String PRINT_ORDER_MESSAGE = "%s\t\t%d\t%d";
    private static final String GIFT_MESSAGE = "=============증\t정===============";
    private static final String PRINT_GIFT_MESSAGE = "%s\t\t%d";
    private static final String RESULT_MESSAGE = "====================================";
    private static final String TOTAL_PRICE_MESSAGE = "총구매액\t\t%d\t%,d";
    private static final String GIFT_SALE_MESSAGE = "행사할인\t\t\t-%,d";
    private static final String MEMBERSHIP_SALE_MESSAGE = "멤버십할인\t\t\t-%,d";
    private static final String RESULT_PRICE_MESSAGE = "내실돈\t\t\t%,d";

    public void printProductions(final Products inputProducts) {
        List<Product> products = inputProducts.getProductions();
        StringBuilder sb = buildProductionString(inputProducts, products);
        System.out.println(sb);
    }

    private StringBuilder buildProductionString(final Products inputProducts, final List<Product> products) {
        StringBuilder sb = new StringBuilder();
        sb.append(WELCOME_MESSAGE).append(lineSeparator());
        for (Product product : products) {
            buildProcess(inputProducts, product, sb);
        }
        return sb;
    }

    private void buildProcess(final Products inputProducts, final Product product, final StringBuilder sb) {
        String name = product.getName();
        String promotion = getPromotion(product);
        int price = product.getPrice();
        String quantityString = getQuantityString(inputProducts.getQuantity(product));
        sb.append(String.format(PRODUCTION_FORMAT_MESSAGE, name, price, quantityString, promotion))
            .append(lineSeparator());
    }

    private static String getPromotion(final Product product) {
        String promotion = product.getPromotion();
        if (promotion == null) {
            promotion = BLANK;
        }
        return promotion;
    }

    private String getQuantityString(final int quantity) {
        if (quantity == NOT_EXIST_VALUE) {
            return NONE_PRODUCTION_MESSAGE;
        }
        return quantity + COUNT_UNIT;
    }
    private int totalOrderCount;
    private int totalOrderPrice;
    int totalGiftSalePrice;
    public void printReceipt(final Orders orders, final Products products, final Gifts gifts, final int salePrice) {
        System.out.println(RECEIPT_MESSAGE);
        printOrderHistory(orders, products);
        printGiftHistory(products, gifts);
        printResult(salePrice);
        System.out.println();
    }

    private void printGiftHistory(final Products products, final Gifts gifts) {
        System.out.println(GIFT_MESSAGE);
        totalGiftSalePrice = NOT_EXIST_VALUE;
        for (Gift gift : gifts.get()) {
            System.out.println(String.format(PRINT_GIFT_MESSAGE, gift.getName(), gift.getQuantity()));
            totalGiftSalePrice += gift.getQuantity() * products.getProductPrice(gift.getName());
        }
    }

    private void printOrderHistory(final Orders orders, final Products products) {
        System.out.println(ORDER_MESSAGE);
        totalOrderCount = NOT_EXIST_VALUE;
        totalOrderPrice = NOT_EXIST_VALUE;
        for (Order order : orders.getOrders()) {
            int orderPrice = order.getQuantity() * products.getProductPrice(order.getName());
            System.out.println(String.format(PRINT_ORDER_MESSAGE, order.getName(), order.getQuantity(), orderPrice));
            totalOrderPrice += orderPrice;
            totalOrderCount += order.getQuantity();
        }
    }

    private void printResult(final int salePrice) {
        System.out.println(RESULT_MESSAGE);
        System.out.println(String.format(TOTAL_PRICE_MESSAGE, totalOrderCount, totalOrderPrice));
        System.out.println(String.format(GIFT_SALE_MESSAGE, totalGiftSalePrice));
        System.out.println(String.format(MEMBERSHIP_SALE_MESSAGE, salePrice));
        System.out.println(String.format(RESULT_PRICE_MESSAGE, totalOrderPrice - totalGiftSalePrice - salePrice));
    }
}
