package capstone.walkreen.dto;

import capstone.walkreen.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidMonthDailyException extends BaseException {
    private final static String message = "올바르지 않은 범위의 날짜입니다";

    private final static HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public InvalidMonthDailyException() {
        super(message, httpStatus);
    }
}
