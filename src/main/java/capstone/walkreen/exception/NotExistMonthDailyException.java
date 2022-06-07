package capstone.walkreen.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotExistMonthDailyException extends BaseException {
    private final static String message = "해당 월에 완료한 데일리 미션 내역이 존재하지 않습니다";

    private final static HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public NotExistMonthDailyException() {
        super(message, httpStatus);
    }
}
