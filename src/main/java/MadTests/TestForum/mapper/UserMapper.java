package MadTests.TestForum.mapper;

import MadTests.TestForum.dto.UserEditRegDTO;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserRegDTO toUserRegDTO(UserEntity entity);

    public abstract UserEditRegDTO toUserEditRegDTO(UserEntity entity);
}
