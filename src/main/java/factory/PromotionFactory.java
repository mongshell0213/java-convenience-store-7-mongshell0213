package factory;

import constants.Constants;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.Promotions;
import validation.Validation;

public class PromotionFactory {
    private static final String IO_EXCEPTION_ERROR = "[ERROR] 입출력 작업 예외입니다.";
    private static final String FILE_NOT_FOUND_ERROR = "[ERROR] 파일이 존재 하지 않습니다.";
    private static final String FILE_INPUT_FORMAT_ERROR="[ERROR] 파일 입력 형식 오류입니다.";
    private static final int PromotionAttributeCount = 5;
    public static void input(Promotions promotions){
        try{
            BufferedReader br = new BufferedReader(new FileReader(Constants.PROMOTIONS_FILE_PATH));
            process(promotions,br);
        }catch (FileNotFoundException e){
            throw new IllegalArgumentException(FILE_NOT_FOUND_ERROR);
        } catch (IOException e) {
            throw new IllegalArgumentException(IO_EXCEPTION_ERROR);
        }
    }

    private static void process(Promotions promotions,BufferedReader br) throws IOException {
        String inputLine;
        br.readLine();
        while((inputLine = br.readLine())!=null){
            String[] strings = inputLine.split(Constants.DELIMITER);
            validate(strings);
        }
    }

    private static void validate(String[] strings){
        validateAttributeCount(strings);
        validateBlank(strings);
        validateNumber(strings);
        validateDateFormat(strings);
    }

    private static void validateAttributeCount(String[] strings) {
        if(strings.length != PromotionAttributeCount)
            throw new IllegalArgumentException(FILE_INPUT_FORMAT_ERROR);
    }

    private static void validateBlank(String[] strings) {
        for (String string : strings) {
            Validation.blank(string);
        }
    }

    private static void validateDateFormat(String[] strings) {
        for(int i=Constants.PROMOTION_START_DATE_POSITION; i<=Constants.PROMOTION_END_DATE_POSITION;i++){
            Validation.datePattern(strings[i]);
        }
    }

    private static void validateNumber(String[] strings) {
        for(int i=Constants.PROMOTION_BUY_POSITION; i<=Constants.PROMOTION_GET_POSITION;i++){
            Validation.number(strings[i]);
        }
    }
}
