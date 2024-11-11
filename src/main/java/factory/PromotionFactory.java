package factory;

import static error.ErrorMessage.FILE_INPUT_FORMAT_ERROR;
import static error.ErrorMessage.FILE_NOT_FOUND_ERROR;
import static error.ErrorMessage.IO_EXCEPTION_ERROR;

import constants.Constants;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Promotion;
import model.Promotions;
import validation.Validation;

public class PromotionFactory {

    private static final int PromotionAttributeCount = 5;

    public static void input(Promotions promotions) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Constants.PROMOTIONS_FILE_PATH));
            process(promotions, br);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(FILE_NOT_FOUND_ERROR.getMessage());
        } catch (IOException e) {
            throw new IllegalArgumentException(IO_EXCEPTION_ERROR.getMessage());
        }
    }

    private static void process(Promotions promotions, BufferedReader br) throws IOException {
        String inputLine;
        br.readLine();
        while ((inputLine = br.readLine()) != null) {
            String[] strings = inputLine.split(Constants.DELIMITER);
            validate(strings);
            Promotion promotion = stringsToPromotion(strings);
            promotions.add(promotion);
        }
    }

    private static Promotion stringsToPromotion(String[] strings) {
        String name = strings[Constants.PROMOTION_NAME_POSITION];
        int buy = Integer.parseInt(strings[Constants.PROMOTION_BUY_POSITION]);
        int get = Integer.parseInt(strings[Constants.PROMOTION_GET_POSITION]);
        LocalDate start_date = stringToLocalDate(strings[Constants.PROMOTION_START_DATE_POSITION]);
        LocalDate end_date = stringToLocalDate(strings[Constants.PROMOTION_END_DATE_POSITION]);
        return new Promotion(name, buy, get, start_date, end_date);
    }

    private static LocalDate stringToLocalDate(String string) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        LocalDate date = LocalDate.parse(string, dateTimeFormatter);
        return date;
    }

    private static void validate(String[] strings) {
        validateAttributeCount(strings);
        validateBlank(strings);
        validateNumber(strings);
        validateDateFormat(strings);
    }

    private static void validateAttributeCount(String[] strings) {
        if (strings.length != PromotionAttributeCount) {
            throw new IllegalArgumentException(FILE_INPUT_FORMAT_ERROR.getMessage());
        }
    }

    private static void validateBlank(String[] strings) {
        for (String string : strings) {
            Validation.blank(string);
        }
    }

    private static void validateDateFormat(String[] strings) {
        for (int i = Constants.PROMOTION_START_DATE_POSITION; i <= Constants.PROMOTION_END_DATE_POSITION; i++) {
            Validation.datePattern(strings[i]);
        }
    }

    private static void validateNumber(String[] strings) {
        for (int i = Constants.PROMOTION_BUY_POSITION; i <= Constants.PROMOTION_GET_POSITION; i++) {
            Validation.number(strings[i]);
        }
    }
}
