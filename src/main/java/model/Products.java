package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Products {

    Map<Product,Integer> productions;
    public static final String OVER_BUY_ERROR = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    public static final String EXIST_ERROR="[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
    public Products() {
        productions = new LinkedHashMap<>();
    }

    public void add(Product product,int amount) {
        productions.put(product,amount);
    }

    public int size() {
        return productions.size();
    }

    /*
    public void buy(Product buyProduct,int buyAmount){
        exist(buyProduct);
        int productionAmount = productions.get(buyProduct);
        overBuy(buyProduct,buyAmount);
        productions.put(buyProduct,productionAmount-buyAmount);
    }


    private void overBuy(Product buyProduct,int buyAmount) {
        int productionAmount = productions.get(buyProduct);
        if (productionAmount < buyAmount) {
            throw new IllegalArgumentException(OVER_BUY_ERROR);
        }
    }

     */

    public int getQuantity(Product product){
        return productions.get(product);
    }

    public List<Product> getProductions(){
        return new ArrayList<>(productions.keySet());
    }

    private void exist(Product product){
        if(productions.get(product)==null){
            throw new IllegalArgumentException(EXIST_ERROR);
        }
    }
}
