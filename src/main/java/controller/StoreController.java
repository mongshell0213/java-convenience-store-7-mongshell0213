package controller;

import model.Order;
import model.Orders;
import model.Products;
import model.Promotions;
import service.StoreService;

public class StoreController {
    private final StoreService storeService;
    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    public void run(){
        //안내메시지, 보유 상품 출력
        Products products = storeService.createProducts();
        storeService.printProducts(products);
        Orders orders = new Orders();
        storeService.readOrders(orders);
        Promotions promotions = storeService.createPromotions();
        for(Order order: orders.getOrders()) {
            storeService.buyProducts(products, order, promotions);
        }
    }
}
