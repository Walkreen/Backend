package capstone.walkreen.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class NonExistMissionException extends BaseException {
    private final static String message = "해당하는 미션이 존재하지 않습니다";

    private final static HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public NonExistMissionException() {
        super(message, httpStatus);
    }
}
