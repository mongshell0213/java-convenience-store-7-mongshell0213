package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Products {

    Map<Product, Integer> productions;
    public static final String OVER_BUY_ERROR = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    public static final String EXIST_ERROR = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";

    public Products() {
        productions = new LinkedHashMap<>();
    }

    public void add(Product product, int amount) {
        productions.put(product, amount);
    }

    public int size() {
        return productions.size();
    }


    public int buy(String orderName, int quantity, String promotionName) {
        List<Product> products = getProductions();
        int price=0;
        for (Product product : products) {
            if (product.isSameName(orderName)
                && Objects.equals(product.getPromotion(), promotionName)) {
                reduce(product, quantity);
                price = product.getPrice() * quantity;
            }
        }
        return price;
    }

    private void reduce(Product product, int reduceQuantity) {
        if(isOverBuy(product, reduceQuantity))
            throw new IllegalArgumentException(OVER_BUY_ERROR);
        int productionAmount = productions.get(product);
        productions.put(product, productionAmount - reduceQuantity);
    }

    public boolean isOverBuy(Product buyProduct, int buyAmount) {
        int productionAmount = productions.get(buyProduct);
        return productionAmount < buyAmount;
    }


    public int getQuantity(Product product) {
        return productions.get(product);
    }

    public List<Product> getProductions() {
        return new ArrayList<>(productions.keySet());
    }

    public boolean isExist(Order order) {
        String orderName = order.getName();
        List<Product> products = getProductions();
        for (Product product : products) {
            if (product.isSameName(orderName)) {
                return true;
            }
        }
        throw new IllegalArgumentException(EXIST_ERROR);
    }

    public String getPromotionName(Order order) {
        String promotionName = null;
        String orderName = order.getName();
        List<Product> products = getProductions();
        for (Product product : products) {
            if (product.isSameName(orderName) && promotionName == null) {
                promotionName = product.getPromotion();
            }
        }
        return promotionName;
    }
}
