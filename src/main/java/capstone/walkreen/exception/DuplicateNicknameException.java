package capstone.walkreen.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicateNicknameException extends BaseException {

    private final static String message = "중복된 닉네임이 존재합니다";

    private final static HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public DuplicateNicknameException() {
        super(message, httpStatus);
    }

}
