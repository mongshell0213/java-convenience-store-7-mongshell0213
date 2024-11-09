package service;

import factory.OrderFactory;
import factory.ProductFactory;
import factory.PromotionFactory;
import java.time.LocalDate;
import java.util.Optional;
import model.Order;
import model.Orders;
import model.Product;
import model.Products;
import model.Promotion;
import model.Promotions;
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

    public void buyProducts(Products products, Order order, Promotions promotions) {
        products.isExist(order);
        //프로모션 여부에 따라 null or 프로모션 이름
        String promotionName = products.getPromotionName(order);
        Optional<Promotion> buyPromotion = promotions.isPromotion(LocalDate.now(), promotionName);
        process(products, order, buyPromotion);
    }


    private int process(Products products, Order order, Optional<Promotion> buyPromotion) {
        String orderName = order.getName();
        int buyQuantity = order.getQuantity();
        if (!buyPromotion.isPresent()) {
            products.buy(orderName, buyQuantity, null);
            return buyQuantity * order.getQuantity();
        }

        Promotion promotion = buyPromotion.get();
        boolean moreFree = promotion.getMoreFree(order.getQuantity());
        if(moreFree){
            InputView inputView = new InputView();
            String answer = inputView.moreFreeProduct(orderName);
            //answer 검증
        }
        return 0;
    }


}
