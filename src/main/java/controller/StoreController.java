package controller;

import factory.SalePriceFactory;
import model.Gifts;
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
        storeService.printProducts();
        storeService.readOrders();
        Promotions promotions = storeService.createPromotions();
        Gifts gifts = new Gifts();
        int totalPrice=0;
        totalPrice += storeService.buyProducts(gifts);
        int salePrice = storeService.membershipSale(totalPrice);
        System.out.println(totalPrice+","+salePrice);
    }
}
