package MadTests.TestForum.mapper;

import MadTests.TestForum.dto.CommentDTO;
import MadTests.TestForum.dto.ThemeDTO;
import MadTests.TestForum.dto.UserEditRegDTO;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.model.CommentEntity;
import MadTests.TestForum.model.ThemeEntity;
import MadTests.TestForum.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class EntityDtoMapper {

    public abstract UserRegDTO toUserRegDTO(UserEntity entity);

    public abstract UserEditRegDTO toUserEditRegDTO(UserEntity entity);

    @Mapping(target = "section", source = "section.name")
    @Mapping(target = "themeCreator", source = "themeCreator.sign")
    public abstract ThemeDTO toThemeDTO(ThemeEntity entity);

    @Mapping(target = "theme", source = "theme.theme")
    @Mapping(target = "user", source = "commCreator.sign")
    public abstract CommentDTO toCommentDTO(CommentEntity entity);
}
