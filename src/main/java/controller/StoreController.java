package controller;

import repository.Productions;
import service.StoreService;

public class StoreController {
    private final StoreService storeService;
    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    public void run(){
        //안내메시지, 보유 상품 출력
        Productions productions = storeService.createProductions();
        storeService.printProductions(productions);
    }
}
