package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.Production;

public class Productions {

    Map<Production,Integer> productions;
    public static final String OVER_BUY_ERROR = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";

    public Productions() {
        productions = new HashMap<>();
    }

    public void add(Production production,int amount) {
        productions.put(production,amount);
    }

    public int size() {
        return productions.size();
    }

    /*
    public void buy(Production buyProduction){
        int buyAmount = buyProduction.getAmount();
        Production production = productions.get(getProductionPosition(buyProduction));
        int productionAmount = production.getAmount();

        //productions.set()
    }

     */

    public void overBuy(Production buyProduction,int buyAmount) {
        int productionAmount = productions.get(buyProduction);
        if (productionAmount < buyAmount) {
            throw new IllegalArgumentException(OVER_BUY_ERROR);
        }
    }
}
