package validation;

import model.Production;
import repository.Productions;

public class Validation {
    private static final String INPUT_BLANK_ERROR="[ERROR] 비어있는 문자열입니다.";
    private static final String NUMBER_FORMAT_ERROR="[ERROR] 숫자형식이 아닙니다.";
    public static void blank(String string){
        if(string == null || string.isBlank()){
            throw new IllegalArgumentException(INPUT_BLANK_ERROR);
        }
    }

    public static void number(String string){
        try{
            int num = Integer.parseInt(string);
        }catch(NumberFormatException e){
            throw new IllegalArgumentException(NUMBER_FORMAT_ERROR);
        }
    }

}
