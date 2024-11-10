package service;

import constants.Constants;
import factory.OrderFactory;
import factory.ProductFactory;
import factory.PromotionFactory;
import factory.SalePriceFactory;
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
import policy.Sale8000Policy;
import policy.SalePercentPolicy;
import policy.SalePolicy;
import validation.Validation;
import view.InputView;
import view.OutputView;

public class StoreService {

    private Products products;
    private Orders orders;
    private Promotions promotions;
    private Gifts gifts;
    private int salePrice;

    public Products createProducts() {
        products = new Products();
        ProductFactory.input(products);
        return products;
    }

    public void printProducts() {
        OutputView outputView = new OutputView();
        outputView.printProductions(products);
    }

    public void readOrders() {
        InputView inputView = new InputView();
        String orderString = inputView.readItem();
        orders = new Orders();
        OrderFactory.add(orders, orderString);
    }

    public Promotions createPromotions() {
        promotions = new Promotions();
        PromotionFactory.input(promotions);
        return promotions;
    }

    public int buyProducts() {
        int totalPrice = 0;
        gifts = new Gifts();
        for (Order order : orders.getOrders()) {
            products.isExist(order);
            String promotionName = products.getPromotionName(order);
            Optional<Promotion> buyPromotion = promotions.isPromotion(LocalDate.now(), promotionName);
            totalPrice += getTotalPrice(order, buyPromotion);
        }
        return totalPrice;
    }

    public int membershipSale(int totalPrice) {
        salePrice = 0;
        InputView inputView = new InputView();
        String answer = inputView.checkMembership();
        validate(answer);
        if (answer.equals(Constants.YES_ANSWER)) {
            salePrice = SalePriceFactory.salePrice(totalPrice);
        }
        return salePrice;
    }

    public void printReceipt() {
        OutputView outputView = new OutputView();
        outputView.printReceipt(orders, products, gifts, salePrice);
    }


    private int getTotalPrice(Order order, Optional<Promotion> buyPromotion) {
        //10줄 이하 리팩토링
        String orderName = order.getName();
        int orderQuantity = order.getQuantity();
        if (!buyPromotion.isPresent()) {
            return products.buy(orderName, orderQuantity, null);
        }
        return promotionPresent(order, buyPromotion);
    }

    private int promotionPresent(Order order, Optional<Promotion> buyPromotion) {
        Promotion promotion = buyPromotion.get();
        int orderQuantity = order.getQuantity();
        boolean possibleMoreFree = promotion.possibleMoreFree(orderQuantity);
        if (possibleMoreFree) {
            orderQuantity = orderQuantityDecision(order.getName(), orderQuantity);
            updateOrder(order, orderQuantity);
            return process(promotion, orderQuantity, order.getName());
        }
        return process(promotion, orderQuantity, order.getName());
    }

    private void updateOrder(Order order, int newQuantity) {
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

    private int process(Promotion promotion, int orderQuantity, String orderName) {
        int buyCount = promotion.getBuyCount(orderQuantity);
        int freeCount = promotion.getFreeCount(orderQuantity);

        Product promotionProduct = infoToProduct(orderName, promotion.getName());
        if (products.isOverBuy(promotionProduct, orderQuantity)) {
            return notEnoughPromotion(promotionProduct, orderName, promotion, orderQuantity);
        }
        return enoughPromotion(promotion, orderName, freeCount, buyCount);
    }

    private int enoughPromotion(Promotion promotion, String orderName,
        int freeCount, int buyCount) {
        products.buy(orderName, freeCount, promotion.getName());
        gifts.add(new Gift(orderName, freeCount));
        return products.buy(orderName, buyCount, promotion.getName());
    }

    private int notEnoughPromotion(Product promotionProduct, String orderName, Promotion promotion, int orderQuantity) {
        InputView inputView = new InputView();
        //최대 가능 갯수
        int maxPossiblePromotion = products.getMaxPossiblePromotion(promotionProduct, promotion);
        String answer = inputView.purchaseAtPrice(orderName, orderQuantity - maxPossiblePromotion);
        validate(answer);
        int buyCount = promotion.getBuyCount(maxPossiblePromotion);
        int freeCount = promotion.getFreeCount(maxPossiblePromotion);
        int totalPrice = 0;
        if (answer.equals(Constants.YES_ANSWER)) {
            products.buy(orderName, freeCount, promotion.getName());
            gifts.add(new Gift(orderName, freeCount));
            totalPrice += products.buy(orderName, buyCount, promotion.getName());
            totalPrice += products.buy(orderName,
                orderQuantity - maxPossiblePromotion - products.getQuantity(promotionProduct), null);
            totalPrice += products.buy(orderName, products.getQuantity(promotionProduct), promotion.getName());
            return totalPrice;
        }
        orderQuantity -= orderQuantity - maxPossiblePromotion;
        return products.buy(orderName, orderQuantity, null);
    }

    private void validate(String answer) {
        Validation.blank(answer);
        Validation.answerPattern(answer);
    }

    private Product infoToProduct(String orderName, String promotionName) {
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
