package view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String READ_ITEM_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요.(예:[사이다-2],[감자칩-1])";
    private static final String MORE_FREE_PRODUCT_MESSAGE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    public String readItem(){
        System.out.println(READ_ITEM_MESSAGE);
        return Console.readLine();
    }

    public String moreFreeProduct(String orderName){
        System.out.println(String.format(MORE_FREE_PRODUCT_MESSAGE,orderName));
        return Console.readLine();
    }
}
