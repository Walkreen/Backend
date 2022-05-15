package capstone.walkreen.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@Getter
public class InvalidUserException extends BaseException {
    private final static String message = "존재하지 않는 계정입니다";

    private final static HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public InvalidUserException() {
        super(message, httpStatus);
    }
}
