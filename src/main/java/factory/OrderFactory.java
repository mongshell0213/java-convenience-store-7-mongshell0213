package factory;

import constants.Constants;
import validation.Validation;

public class OrderFactory {
    public static void add(String input){

    }

    private static void validate(String string){
        Validation.blank(string);
        String[] splitOrder = string.split(Constants.DELIMITER);
        for(String order:splitOrder){
            Validation.pattern(order);
        }
    }
}
