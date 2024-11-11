package model;

import static error.ErrorMessage.EXIST_ERROR;
import static error.ErrorMessage.OVER_BUY_ERROR;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Products {

    Map<Product, Integer> productions;

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
            throw new IllegalArgumentException(OVER_BUY_ERROR.getMessage());
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
        throw new IllegalArgumentException(EXIST_ERROR.getMessage());
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

    public int getNotApplyPromotionCount(Product product,Promotion promotion,int orderQuantity){
        int leftQuantity = productions.get(product);
        if(leftQuantity > orderQuantity){
            return promotion.notApplyPromotionCount(orderQuantity);
        }
        return orderQuantity - promotion.getMax(leftQuantity);
    }

    public int getProductPrice(String productName){
        List<Product> products = getProductions();
        for (Product product : products) {
            if (product.isSameName(productName)) {
                return product.getPrice();
            }
        }
        return 0;
    }

    public int getPromotionNormalQuantity(String productName){
        int count=0;
        List<Product> products = getProductions();
        for (Product product : products) {
            if (product.isSameName(productName)) {
                count+=productions.get(product);
            }
        }
        return count;
    }

    public boolean isPossibleMoreFree(Order order, Promotion promotion){
        List<Product> products = getProductions();
        int leftQuantity=0;
        for(Product product : products){
            if(product.isSameName(order.getName()) && promotion.isSame(product.getPromotion())){
                leftQuantity = productions.get(product);
                break;
            }
        }
        return leftQuantity > order.getQuantity() && promotion.possibleMoreFree(order.getQuantity());
    }

    public boolean isEnoughPromotion(Product promotionProduct,Promotion promotion,Order order){
        int orderQuantity = order.getQuantity();
        int leftQuantity = productions.get(promotionProduct);
        return promotion.getMaxValidPromotion(leftQuantity) >= orderQuantity;
    }

}
