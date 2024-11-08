package service;

import factory.ProductFactory;
import model.Products;
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

    public void readOrders(){
        InputView inputView = new InputView();
        inputView.readItem();
    }
}
