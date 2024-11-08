package repository;

import java.util.LinkedHashMap;
import java.util.Map;
import model.Production;

public class Productions {

    Map<Production,Integer> productions;
    public static final String OVER_BUY_ERROR = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    public static final String EXIST_ERROR="[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
    public Productions() {
        productions = new LinkedHashMap<>();
    }

    public void add(Production production,int amount) {
        productions.put(production,amount);
    }

    public int size() {
        return productions.size();
    }

    public void buy(Production buyProduction,int buyAmount){
        exist(buyProduction);
        int productionAmount = productions.get(buyProduction);
        overBuy(buyProduction,buyAmount);
        productions.put(buyProduction,productionAmount-buyAmount);
    }

    private void overBuy(Production buyProduction,int buyAmount) {
        int productionAmount = productions.get(buyProduction);
        if (productionAmount < buyAmount) {
            throw new IllegalArgumentException(OVER_BUY_ERROR);
        }
    }

    public int getAmount(Production production){
        return productions.get(production);
    }

    private void exist(Production production){
        if(productions.get(production)==null){
            throw new IllegalArgumentException(EXIST_ERROR);
        }
    }
}
