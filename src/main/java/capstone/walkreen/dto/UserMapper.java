package capstone.walkreen.dto;

import capstone.walkreen.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {


    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User requestToUser(SignUpRequest signUpRequest);

    UserResponse userToResponse(User user);
}
