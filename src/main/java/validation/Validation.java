package validation;

import constants.Constants;

public class Validation {

    private static final String INPUT_BLANK_ERROR = "[ERROR] 비어있는 문자열입니다.";
    private static final String NUMBER_FORMAT_ERROR = "[ERROR] 숫자형식이 아닙니다.";
    private static final String INPUT_ERROR = "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.";

    public static void blank(String string) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException(INPUT_BLANK_ERROR);
        }
    }

    public static void number(String string) {
        try {
            int num = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NUMBER_FORMAT_ERROR);
        }
    }

    public static void pattern(String string) {
        if (!string.startsWith(Constants.ORDER_STARTER) || !string.endsWith(Constants.ORDER_ENDER)) {
            throw new IllegalArgumentException(INPUT_ERROR);
        }
        if (string.split(Constants.ORDER_DELIMITER).length != Constants.ORDER_ATTRIBUTE_LEN) {
            throw new IllegalArgumentException(INPUT_ERROR);
        }
        number(string.split(Constants.ORDER_DELIMITER)[1].replace(Constants.ORDER_ENDER,Constants.BLANK));
    }
}
