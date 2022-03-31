package MadTests.TestForum.mapper;

import MadTests.TestForum.dto.UserEditRegDTO;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserRegDTO userRegDTO(UserEntity entity);


    @Mapping(target = "ava", source = "avatar")
    public abstract UserEditRegDTO userEditRegDTO(UserEntity entity);

}
