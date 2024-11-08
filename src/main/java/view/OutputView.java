package view;

import constants.Constants;
import java.util.List;
import model.Product;
import model.Products;

import static java.lang.System.lineSeparator;

public class OutputView {

    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다."
        + lineSeparator() + "현재 보유하고 있는 상품입니다." + lineSeparator();

    private static final String PRODUCTION_FORMAT_MESSAGE = "- %S %,d원 %s %s";
    private static final String NONE_PRODUCTION_MESSAGE = "재고 없음";
    private static final String COUNT_UNIT = "개";

    public void printProductions(Products inputProducts) {
        List<Product> products = inputProducts.getProductions();
        StringBuilder sb = buildProductionString(inputProducts, products);
        System.out.println(sb);
    }

    private StringBuilder buildProductionString(Products inputProducts, List<Product> products) {
        StringBuilder sb = new StringBuilder();
        sb.append(WELCOME_MESSAGE).append(lineSeparator());
        for (Product product : products) {
            process(inputProducts, product, sb);
        }
        return sb;
    }

    private void process(Products inputProducts, Product product, StringBuilder sb) {
        String name = product.getName();
        String promotion = getPromotion(product);
        int price = product.getPrice();
        String quantityString = getQuantityString(inputProducts.getQuantity(product));
        sb.append(String.format(PRODUCTION_FORMAT_MESSAGE, name, price, quantityString, promotion))
            .append(lineSeparator());
    }

    private static String getPromotion(Product product) {
        String promotion = product.getPromotion();
        if (promotion == null) {
            promotion = Constants.BLANK;
        }
        return promotion;
    }

    private String getQuantityString(int quantity) {
        if (quantity == 0) {
            return NONE_PRODUCTION_MESSAGE;
        }
        return quantity + COUNT_UNIT;
    }
}
