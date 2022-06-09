package capstone.walkreen.entity;

import capstone.walkreen.enumerations.Authority;
import capstone.walkreen.enumerations.Gender;

import java.util.*;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Setter
public class User extends BaseEntity {

    private String email;

    @Setter
    private String password;

    private String name;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Date birthday;

    private Integer prepoint;

    private Integer accpoint;

    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.USER;

    @OneToMany(mappedBy = "user")
    private List<Daily> dailyMission = new ArrayList<>();

}
