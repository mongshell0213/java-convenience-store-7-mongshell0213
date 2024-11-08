package repository;

import constants.Constants;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.Production;
import validation.Validation;

public class ProductionFactory {

    private static final String IO_EXCEPTION_ERROR = "[ERROR] 입출력 작업 예외입니다.";
    private static final String FILE_NOT_FOUND_ERROR = "[ERROR] 파일이 존재 하지 않습니다.";

    public static void input(Productions productions) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Constants.PRODUCTS_FILE_PATH));
            process(productions, br);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(FILE_NOT_FOUND_ERROR);
        } catch (IOException e) {
            throw new IllegalArgumentException(IO_EXCEPTION_ERROR);
        }
    }

    private static void process(Productions productions, BufferedReader br) throws IOException {
        String inputLine;
        int count = 0;
        boolean prePromotionProduct = false;
        Production production=null;
        while ((inputLine = br.readLine()) != null) {
            if (count == 0) {
                count++;
                continue;
            }
            if(prePromotionProduct){
                productions.add(production.getNoneExistNormalProduct(),0);
                prePromotionProduct = false;
            }
            production = add(productions, inputLine.split(Constants.DELIMITER));
            if(production.isPromotionProduct()){
                prePromotionProduct = true;
            }
        }
    }


    private static Production add(Productions productions, String[] inputLine) {
        validate(inputLine);

        String name = inputLine[Constants.NAME_POSITION];
        int price = Integer.parseInt(inputLine[Constants.PRICE_POSITION]);
        int quantity = Integer.parseInt(inputLine[Constants.QUANTITY_POSITION]);
        String promotion = inputLine[Constants.PROMOTION_POSITION];
        if(promotion.equals("null"))
            promotion = null;
        productions.add(new Production(name, price, promotion),quantity);
        return new Production(name, price, promotion);
    }

    private static void validate(String[] input) {
        for (String string : input) {
            Validation.blank(string);
        }
        Validation.number(input[Constants.PRICE_POSITION]);
        Validation.number(input[Constants.QUANTITY_POSITION]);
    }
}
