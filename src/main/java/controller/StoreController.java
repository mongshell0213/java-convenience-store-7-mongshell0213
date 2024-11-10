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
        storeService.createProducts();
        while(true) {
            storeService.printProducts();
            storeService.readOrders();
            storeService.createPromotions();
            storeService.buyProducts();
            storeService.membershipSale();
            storeService.printReceipt();
            if(!storeService.isBuyMoreProducts()) {
                break;
            }
        }
    }
}
