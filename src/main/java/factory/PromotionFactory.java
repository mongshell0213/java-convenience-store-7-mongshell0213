package factory;

import static constants.Constants.DATE_FORMAT;
import static constants.Constants.DELIMITER;
import static constants.Constants.PROMOTION_ATTRIBUTE_COUNT;
import static constants.Constants.PROMOTION_BUY_POSITION;
import static constants.Constants.PROMOTION_END_DATE_POSITION;
import static constants.Constants.PROMOTION_GET_POSITION;
import static constants.Constants.PROMOTION_NAME_POSITION;
import static constants.Constants.PROMOTION_START_DATE_POSITION;
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

    public static void input(final Promotions promotions) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Constants.PROMOTIONS_FILE_PATH));
            process(promotions, br);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(FILE_NOT_FOUND_ERROR.getMessage());
        } catch (IOException e) {
            throw new IllegalArgumentException(IO_EXCEPTION_ERROR.getMessage());
        }
    }

    private static void process(final Promotions promotions, BufferedReader br) throws IOException {
        String inputLine;
        br.readLine();
        while ((inputLine = br.readLine()) != null) {
            String[] strings = inputLine.split(DELIMITER);
            validate(strings);
            Promotion promotion = stringsToPromotion(strings);
            promotions.add(promotion);
        }
    }

    private static Promotion stringsToPromotion(final String[] strings) {
        String name = strings[PROMOTION_NAME_POSITION];
        int buy = Integer.parseInt(strings[PROMOTION_BUY_POSITION]);
        int get = Integer.parseInt(strings[PROMOTION_GET_POSITION]);
        LocalDate start_date = stringToLocalDate(strings[PROMOTION_START_DATE_POSITION]);
        LocalDate end_date = stringToLocalDate(strings[PROMOTION_END_DATE_POSITION]);
        return new Promotion(name, buy, get, start_date, end_date);
    }

    private static LocalDate stringToLocalDate(final String string) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate date = LocalDate.parse(string, dateTimeFormatter);
        return date;
    }

    private static void validate(final String[] strings) {
        validateAttributeCount(strings);
        validateBlank(strings);
        validateNumber(strings);
        validateDateFormat(strings);
    }

    private static void validateAttributeCount(final String[] strings) {
        if (strings.length != PROMOTION_ATTRIBUTE_COUNT) {
            throw new IllegalArgumentException(FILE_INPUT_FORMAT_ERROR.getMessage());
        }
    }

    private static void validateBlank(final String[] strings) {
        for (String string : strings) {
            Validation.blank(string);
        }
    }

    private static void validateDateFormat(final String[] strings) {
        for (int i = PROMOTION_START_DATE_POSITION; i <= PROMOTION_END_DATE_POSITION; i++) {
            Validation.datePattern(strings[i]);
        }
    }

    private static void validateNumber(final String[] strings) {
        for (int i = PROMOTION_BUY_POSITION; i <= PROMOTION_GET_POSITION; i++) {
            Validation.number(strings[i]);
        }
    }
}
