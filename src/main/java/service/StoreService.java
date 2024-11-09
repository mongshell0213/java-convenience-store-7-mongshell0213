package service;

import factory.OrderFactory;
import factory.ProductFactory;
import factory.PromotionFactory;
import model.Order;
import model.Orders;
import model.Products;
import model.Promotions;
import view.InputView;
import view.OutputView;

public class StoreService {

    public Products createProducts() {
        Products products = new Products();
        ProductFactory.input(products);
        return products;
    }

    public void printProducts(Products products){
        OutputView outputView = new OutputView();
        outputView.printProductions(products);
    }

    public void readOrders(Orders orders){
        InputView inputView = new InputView();
        String orderString = inputView.readItem();
        OrderFactory.add(orders,orderString);
    }

    public Promotions createPromotions() {
        Promotions promotions = new Promotions();
        PromotionFactory.input(promotions);
        return promotions;
    }

    public void buyProducts(Products products,Orders orders,Promotions promotions){
        for(Order order:orders.getOrders()) {
            products.isExist(order);

        }
    }
}
