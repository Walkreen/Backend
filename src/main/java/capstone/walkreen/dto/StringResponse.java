package capstone.walkreen.dto;

import capstone.walkreen.auth.TokenResponse;
import capstone.walkreen.enumerations.Gender;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StringResponse {

    private String message;
}
