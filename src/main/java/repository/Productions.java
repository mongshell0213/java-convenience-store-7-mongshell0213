package repository;

import java.util.ArrayList;
import java.util.List;
import model.Production;

public class Productions {

    List<Production> productions;
    public static final String OVER_BUY_ERROR = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";

    public Productions() {
        productions = new ArrayList<>();
    }

    public void add(Production production) {
        productions.add(production);
    }

    public int size() {
        return productions.size();
    }

    public void overBuy(Production buyProduction) {
        int buyAmount = buyProduction.getAmount();
        int productionAmount = getProductionAmount(buyProduction);
        if (productionAmount < buyAmount) {
            throw new IllegalArgumentException(OVER_BUY_ERROR);
        }
    }

    private int getProductionAmount(Production buyProduction) {
        int productionAmount = 0;
        for (Production production : productions) {
            if (production == buyProduction) {
                productionAmount = buyProduction.getAmount();
            }
        }
        return productionAmount;
    }
}
