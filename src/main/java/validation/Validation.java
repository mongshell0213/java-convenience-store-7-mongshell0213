package validation;

import static constants.Constants.BLANK;
import static constants.Constants.DATE_FORMAT;
import static constants.Constants.MIN_ORDER_COUNT;
import static constants.Constants.NO_ANSWER;
import static constants.Constants.ORDER_ATTRIBUTE_LEN;
import static constants.Constants.ORDER_DELIMITER;
import static constants.Constants.ORDER_ENDER;
import static constants.Constants.ORDER_STARTER;
import static constants.Constants.YES_ANSWER;
import static error.ErrorMessage.DATE_FORMAT_ERROR;
import static error.ErrorMessage.INPUT_BLANK_ERROR;
import static error.ErrorMessage.INPUT_ERROR;
import static error.ErrorMessage.NUMBER_FORMAT_ERROR;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import policy.Sale8000Policy;
import policy.SalePercentPolicy;
import policy.SalePolicy;

public class Validation {

    public static void blank(final String string) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException(INPUT_BLANK_ERROR.getMessage());
        }
    }

    public static void number(final String string) {
        int num;
        try {
            num = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NUMBER_FORMAT_ERROR.getMessage());
        }
        if (num < MIN_ORDER_COUNT) {
            throw new IllegalArgumentException(INPUT_ERROR.getMessage());
        }
    }

    public static void orderPattern(final String string) {
        if (!string.startsWith(ORDER_STARTER) || !string.endsWith(ORDER_ENDER)) {
            throw new IllegalArgumentException(INPUT_ERROR.getMessage());
        }
        if (string.split(ORDER_DELIMITER).length != ORDER_ATTRIBUTE_LEN) {
            throw new IllegalArgumentException(INPUT_ERROR.getMessage());
        }
        number(string.split(ORDER_DELIMITER)[1].replace(ORDER_ENDER, BLANK));
    }

    public static void datePattern(final String string) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try {
            LocalDate date = LocalDate.parse(string, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(DATE_FORMAT_ERROR.getMessage());
        }
    }

    public static void answerPattern(final String answer) {
        if (!answer.equals(YES_ANSWER) && !answer.equals(NO_ANSWER)) {
            throw new IllegalArgumentException(INPUT_ERROR.getMessage());
        }
    }

    public static boolean isMaxSale(final int payPrice) {
        SalePolicy sale8000Policy = new Sale8000Policy();
        SalePolicy salePercentPolicy = new SalePercentPolicy();

        return sale8000Policy.salePrice(payPrice) <= salePercentPolicy.salePrice(payPrice);
    }
}
