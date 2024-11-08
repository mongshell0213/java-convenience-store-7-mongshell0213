package service;

import repository.ProductionFactory;
import repository.Productions;
import view.OutputView;

public class StoreService {

    public Productions createProductions() {
        Productions productions = new Productions();
        ProductionFactory.input(productions);
        return productions;
    }

    public void printProductions(Productions productions){
        OutputView outputView = new OutputView();
        outputView.printProductions(productions);
    }
}
