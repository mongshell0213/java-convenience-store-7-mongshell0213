package service;

import repository.ProductionFactory;
import repository.Productions;

public class StoreService {

    public Productions createProductions() {
        Productions productions = new Productions();
        ProductionFactory.input(productions);
        return productions;
    }
}
