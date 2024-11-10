package service;

import constants.Constants;
import factory.OrderFactory;
import factory.ProductFactory;
import factory.PromotionFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import model.Gift;
import model.Gifts;
import model.Order;
import model.Orders;
import model.Product;
import model.Products;
import model.Promotion;
import model.Promotions;
import policy.SalePolicy;
import validation.Validation;
import view.InputView;
import view.OutputView;

public class StoreService {

    public Products createProducts() {
        Products products = new Products();
        ProductFactory.input(products);
        return products;
    }

    public void printProducts(Products products) {
        OutputView outputView = new OutputView();
        outputView.printProductions(products);
    }

    public void readOrders(Orders orders) {
        InputView inputView = new InputView();
        String orderString = inputView.readItem();
        OrderFactory.add(orders, orderString);
    }

    public Promotions createPromotions() {
        Promotions promotions = new Promotions();
        PromotionFactory.input(promotions);
        return promotions;
    }

    public int buyProducts(Products products, Orders orders, Promotions promotions, Gifts gifts) {
        int totalPrice = 0;
        for (Order order : orders.getOrders()) {
            products.isExist(order);
            String promotionName = products.getPromotionName(order);
            Optional<Promotion> buyPromotion = promotions.isPromotion(LocalDate.now(), promotionName);
            totalPrice += getTotalPrice(products, orders, order, buyPromotion, gifts);
        }
        return totalPrice;
    }

    /*
    public int salePrice(int payPrice){
        SalePolicy salePolicy;

    }

     */
    private int getTotalPrice(Products products, Orders orders, Order order, Optional<Promotion> buyPromotion,
        Gifts gifts) {
        //10줄 이하 리팩토링
        String orderName = order.getName();
        int orderQuantity = order.getQuantity();
        if (!buyPromotion.isPresent()) {
            return products.buy(orderName, orderQuantity, null);
        }
        return promotionPresent(products, orders, order, buyPromotion, gifts);
    }

    private int promotionPresent(Products products, Orders orders, Order order, Optional<Promotion> buyPromotion,
        Gifts gifts) {
        Promotion promotion = buyPromotion.get();
        int orderQuantity = order.getQuantity();
        boolean possibleMoreFree = promotion.possibleMoreFree(orderQuantity);
        if (possibleMoreFree) {
            orderQuantity = orderQuantityDecision(order.getName(), orderQuantity);
            updateOrder(orders, order, orderQuantity);
            return process(products, gifts, promotion, orderQuantity, order.getName());
        }
        return process(products, gifts, promotion, orderQuantity, order.getName());
    }

    private void updateOrder(Orders orders, Order order, int newQuantity) {
        orders.update(order, newQuantity);
    }

    private int orderQuantityDecision(String orderName, int orderQuantity) {
        InputView inputView = new InputView();
        String answer = inputView.moreFreeProduct(orderName);
        validate(answer);
        if (answer.equals(Constants.YES_ANSWER)) {
            orderQuantity++;
        }
        return orderQuantity;
    }

    private int process(Products products, Gifts gifts, Promotion promotion, int orderQuantity, String orderName) {
        int buyCount = promotion.getBuyCount(orderQuantity);
        int freeCount = promotion.getFreeCount(orderQuantity);

        Product promotionProduct = infoToProduct(products, orderName, promotion.getName());
        if (products.isOverBuy(promotionProduct, freeCount)) {
            return notEnoughPromotion(products, orderName, freeCount, buyCount);
        }
        return enoughPromotion(products, gifts, promotion, orderName, freeCount, buyCount);
    }

    private static int enoughPromotion(Products products, Gifts gifts, Promotion promotion, String orderName,
        int freeCount,
        int buyCount) {
        products.buy(orderName, freeCount, promotion.getName());
        gifts.add(new Gift(orderName, freeCount));
        return products.buy(orderName, buyCount, null);
    }

    private int notEnoughPromotion(Products products, String orderName, int freeCount, int buyCount) {
        InputView inputView = new InputView();
        String answer = inputView.purchaseAtPrice(orderName, freeCount);
        validate(answer);
        if (answer.equals(Constants.YES_ANSWER)) {
            buyCount += freeCount;
            return products.buy(orderName, buyCount, null);
        }
        buyCount -= freeCount;
        return products.buy(orderName, buyCount, null);
    }

    private void validate(String answer) {
        Validation.blank(answer);
        Validation.answerPattern(answer);
    }

    private Product infoToProduct(Products products, String orderName, String promotionName) {
        List<Product> productList = products.getProductions();
        Product returnProduct = null;
        for (Product product : productList) {
            if (product.isSameName(orderName) && Objects.equals(product.getPromotion(), promotionName)) {
                returnProduct = product;
                break;
            }
        }
        return returnProduct;
    }
}
