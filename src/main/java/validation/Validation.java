package validation;

import static constants.Constants.NO_ANSWER;
import static constants.Constants.YES_ANSWER;

import constants.Constants;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import model.Product;
import model.Products;
import model.Promotion;
import policy.Sale8000Policy;
import policy.SalePercentPolicy;
import policy.SalePolicy;

public class Validation {

    private static final String INPUT_BLANK_ERROR = "[ERROR] 비어있는 문자열입니다.";
    private static final String NUMBER_FORMAT_ERROR = "[ERROR] 숫자형식이 아닙니다.";
    private static final String INPUT_ERROR = "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.";
    private static final String DATE_FORMAT_ERROR = "[ERROR] 잘못된 날짜 형식입니다.";

    public static void blank(String string) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException(INPUT_BLANK_ERROR);
        }
    }

    public static void number(String string) {
        int num;
        try {
            num = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NUMBER_FORMAT_ERROR);
        }
        if(num<1){
            throw new IllegalArgumentException(INPUT_ERROR);
        }
    }

    public static void orderPattern(String string) {
        if (!string.startsWith(Constants.ORDER_STARTER) || !string.endsWith(Constants.ORDER_ENDER)) {
            throw new IllegalArgumentException(INPUT_ERROR);
        }
        if (string.split(Constants.ORDER_DELIMITER).length != Constants.ORDER_ATTRIBUTE_LEN) {
            throw new IllegalArgumentException(INPUT_ERROR);
        }
        number(string.split(Constants.ORDER_DELIMITER)[1].replace(Constants.ORDER_ENDER, Constants.BLANK));
    }

    public static void datePattern(String string) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        try {
            LocalDate date = LocalDate.parse(string, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(DATE_FORMAT_ERROR);
        }
    }

    public static void answerPattern(String answer){
        if(!answer.equals(YES_ANSWER) && !answer.equals(NO_ANSWER)){
            throw new IllegalArgumentException(INPUT_ERROR);
        }
    }

    public static boolean isMaxSale(int payPrice){
        SalePolicy sale8000Policy = new Sale8000Policy();
        SalePolicy salePercentPolicy = new SalePercentPolicy();

        return sale8000Policy.salePrice(payPrice) <= salePercentPolicy.salePrice(payPrice);
    }
}
