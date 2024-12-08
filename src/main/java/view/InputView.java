package view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String READ_ITEM_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요.(예:[사이다-2],[감자칩-1])";
    private static final String MORE_FREE_PRODUCT_MESSAGE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String PURCHASE_PRICE_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private static final String MEMBERSHIP_SALE_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String BUY_MORE_PRODUCT_MESSAGE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
    public String readItem(){
        System.out.println(READ_ITEM_MESSAGE);
        return Console.readLine();
    }

    public String moreFreeProduct(final String orderName){
        System.out.println(String.format(MORE_FREE_PRODUCT_MESSAGE,orderName));
        return Console.readLine();
    }

    public String purchaseAtPrice(final String orderName, int quantity){
        System.out.println(String.format(PURCHASE_PRICE_MESSAGE,orderName,quantity));
        return Console.readLine();
    }

    public String checkMembership(){
        System.out.println(MEMBERSHIP_SALE_MESSAGE);
        return Console.readLine();
    }

    public String buyMoreProducts(){
        System.out.println(BUY_MORE_PRODUCT_MESSAGE);
        return Console.readLine();
    }
}
