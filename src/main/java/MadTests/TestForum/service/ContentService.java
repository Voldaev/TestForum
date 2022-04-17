package MadTests.TestForum.service;

import MadTests.TestForum.dto.CommentDTO;
import MadTests.TestForum.dto.MessageDTO;
import MadTests.TestForum.dto.ThemeDTO;
import MadTests.TestForum.mapper.EntityDtoMapper;
import MadTests.TestForum.model.CommentEntity;
import MadTests.TestForum.model.SectionEntity;
import MadTests.TestForum.model.ThemeEntity;
import MadTests.TestForum.rep.CommentRepository;
import MadTests.TestForum.rep.SectionRepository;
import MadTests.TestForum.rep.ThemeRepository;
import MadTests.TestForum.rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ContentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    EntityDtoMapper entityDtoMapper;

    public List<String> getSectionNames() {
        ArrayList<String> list = new ArrayList<>();
        sectionRepository.findAll().forEach(section -> list.add(section.getName()));
        return list;
    }

    //fixme debug
    public List<ThemeDTO> getSectionContent(String section, int page) {
        int themesPerPage = 5; //fixme добавить опцию выбора количества на странице
        LinkedList<ThemeDTO> list = new LinkedList<>();
        SectionEntity sectionEntity = sectionRepository.getById(section);
        sectionEntity.getThemes().forEach(themeEntity -> {
            ThemeDTO themeDTO = entityDtoMapper.toThemeDTO(themeEntity);
            themeDTO.setComms(themeEntity.getComments().size());
            list.add(themeDTO);
        });
        for (int i = 1; i < (page-1)*themesPerPage;i++){
            list.remove(0);
        }
        while (list.size()>themesPerPage)
            list.removeLast();
        return list;
    }

    public List<ThemeDTO> getTheme(String theme) {
        ArrayList<ThemeDTO> list = new ArrayList<>();
        ThemeDTO themeDTO = entityDtoMapper.toThemeDTO(themeRepository.getByTheme(theme));
        themeDTO.setComms(themeRepository.getByTheme(theme).getComments().size());
        list.add(themeDTO);
        return list;
    }

    public List<CommentDTO> getComms(String theme) {
        ThemeEntity entity = themeRepository.getByTheme(theme);
        ArrayList<CommentDTO> list = new ArrayList<>();
        entity.getComments().forEach(comment -> list.add(entityDtoMapper.toCommentDTO(comment)));
        return list;
    }

    public MessageDTO createTheme(String section, String theme, String content, Long user) {
        ThemeEntity entity = new ThemeEntity();
        entity.setTheme(theme);
        entity.setText(content);
        entity.setThemeCreator(userRepository.getById(user));
        entity.setSection(sectionRepository.getById(section));
        entity.setComments(new ArrayList<>());
        entity.setPublished(LocalDateTime.now());
        entity.setScore(0);
        themeRepository.save(entity);
        return MessageDTO.succeed("Тема успешно создана");
    }

    public MessageDTO createComment(String theme, String sign, Long sessionUserId) {
        CommentEntity entity = new CommentEntity();
        entity.setTheme(themeRepository.getByTheme(theme));
        entity.setText(sign);
        entity.setCommCreator(userRepository.getById(sessionUserId));
        entity.setPublished(LocalDateTime.now());
        entity.setScore(0);
        commentRepository.save(entity);
        return MessageDTO.succeed("Комментарий добавлен");
    }
}
