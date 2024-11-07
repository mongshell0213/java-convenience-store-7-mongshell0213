package store;

import controller.StoreController;
import service.StoreService;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController(new StoreService());
        storeController.run();
    }
}
