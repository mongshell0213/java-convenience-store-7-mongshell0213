package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import model.Production;
import validation.Validation;

public class ProductionFactory {
    private static final String DELIMITER=",";
    private static final int PRICE_POSITION=1;
    private static final int QUANTITY_POSITION=2;
    public static void input(Productions productions) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/products.md"));
        String inputLine;
        int count=0;
        while((inputLine = br.readLine())!=null){
            String[] split = inputLine.split(DELIMITER);
            if(count++==0) continue;

            validate(split);
            add(productions, split);
        }
    }

    private static void add(Productions productions, String[] inputLine) {
        String name = inputLine[0];
        int price = Integer.parseInt(inputLine[1]);
        int quantity = Integer.parseInt(inputLine[2]);
        String promotion = inputLine[3];
        Production production = new Production(name,price,quantity,promotion);
        productions.add(production);
    }

    private static void validate(String[] input){
        for (String string : input) {
            Validation.blank(string);
        }
        Validation.number(input[PRICE_POSITION]);
        Validation.number(input[QUANTITY_POSITION]);
    }
}
