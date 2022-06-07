package capstone.walkreen.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotExistTodayDailyException extends BaseException {
    private final static String message = "오늘 완료한 데일리 미션 내역이 존재하지 않습니다";

    private final static HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public NotExistTodayDailyException() {
        super(message, httpStatus);
    }
}
