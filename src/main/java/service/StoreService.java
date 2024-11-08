package service;

import repository.ProductFactory;
import repository.Products;
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
}
