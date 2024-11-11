package error;

public enum ErrorMessage {
    IO_EXCEPTION_ERROR("[ERROR] 입출력 작업 예외입니다."),
    FILE_NOT_FOUND_ERROR("[ERROR] 파일이 존재 하지 않습니다."),
    FILE_INPUT_FORMAT_ERROR("[ERROR] 파일 입력 형식 오류입니다."),
    OVER_BUY_ERROR("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    EXIST_ERROR("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INPUT_BLANK_ERROR("[ERROR] 비어있는 문자열입니다."),
    NUMBER_FORMAT_ERROR("[ERROR] 숫자형식이 아닙니다."),
    INPUT_ERROR("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    DATE_FORMAT_ERROR("[ERROR] 잘못된 날짜 형식입니다.");
    private final String message;

    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
