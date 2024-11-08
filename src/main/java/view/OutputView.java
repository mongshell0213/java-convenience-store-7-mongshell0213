package view;

import java.util.List;
import model.Production;
import repository.Productions;

import static java.lang.System.lineSeparator;

public class OutputView {

    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다."
        + lineSeparator() + "현재 보유하고 있는 상품입니다." + lineSeparator();

    private static final String PRODUCTION_FORMAT_MESSAGE = "- %S %,d원 %s %s";
    private static final String NONE_PRODUCTION_MESSAGE = "재고 없음";
    private static final String COUNT_UNIT = "개";
    private static final String BLANK = "";

    public void printProductions(Productions inputProductions) {
        List<Production> productions = inputProductions.getProductions();
        StringBuilder sb = buildProductionString(inputProductions, productions);
        System.out.println(sb);
    }

    private StringBuilder buildProductionString(Productions inputProductions, List<Production> productions) {
        StringBuilder sb = new StringBuilder();
        sb.append(WELCOME_MESSAGE).append(lineSeparator());
        for (Production production : productions) {
            process(inputProductions, production, sb);
        }
        return sb;
    }

    private void process(Productions inputProductions, Production production, StringBuilder sb) {
        String name = production.getName();
        String promotion = getPromotion(production);
        int price = production.getPrice();
        String quantityString = getQuantityString(inputProductions.getQuantity(production));
        sb.append(String.format(PRODUCTION_FORMAT_MESSAGE, name, price, quantityString, promotion))
            .append(lineSeparator());
    }

    private static String getPromotion(Production production) {
        String promotion = production.getPromotion();
        if (promotion == null) {
            promotion = BLANK;
        }
        return promotion;
    }

    private String getQuantityString(int quantity) {
        if (quantity == 0) {
            return NONE_PRODUCTION_MESSAGE;
        }
        return quantity + COUNT_UNIT;
    }
}
