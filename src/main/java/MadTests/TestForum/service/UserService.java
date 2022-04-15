package MadTests.TestForum.service;

import MadTests.TestForum.config.DirUtils;
import MadTests.TestForum.dto.*;
import MadTests.TestForum.event.UserRegisteredPublished;
import MadTests.TestForum.mapper.EntityDtoMapper;
import MadTests.TestForum.model.UserEntity;
import MadTests.TestForum.model.enums.Status;
import MadTests.TestForum.rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    EntityDtoMapper entityDtoMapper;

    @Autowired
    DirUtils dirUtils;

    private void setSessionUserId(Long userId) {
        if (userId == null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        } else {
            List<GrantedAuthority> roles = new ArrayList<>(0);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,null, roles);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    public MessageDTO save(UserRegDTO user) {
        UserEntity entity = userRepository.findByEmail(user.getMail());
        if (entity != null) {
            return MessageDTO.failed("Электронная почта уже занята");
        }
        entity = new UserEntity();
        entity.setName(user.getName());
        if (userRepository.findBySign(user.getSign())!=null) {
            return MessageDTO.failed("Логин уже занят, попробуйте другой");
        }
        entity.setSign(user.getSign());
        entity.setPass(passwordEncoder.encode(user.getPass()));
        entity.setMail(user.getMail());
        entity.setStatus(Status.UNCHECKED);
        entity.setUuid(UUID.randomUUID().toString());
        userRepository.save(entity);

        eventPublisher.publishEvent(new UserRegisteredPublished(entity.getSign(), entity.getUuid(), entity.getMail()));
        return MessageDTO.succeed("Успех");
    }

    public MessageDTO check(LoginDTO l) {
        if (userRepository.findBySign(l.getSign())==null) {
            return MessageDTO.failed("Данные не верны");
        }
        if (passwordEncoder.matches(l.getPass(), userRepository.findBySign(l.getSign()).getPass())) {
            setSessionUserId(userRepository.findBySign(l.getSign()).getId());
            return MessageDTO.succeed("Подтверждено");
        } else {
            return MessageDTO.failed("Данные не верны");
        }
    }

    public Status getStatus(Long id) {
        return userRepository.getById(id).getStatus();
    }

    public boolean active(String uuid) {
        UserEntity entity = userRepository.findByUuid(uuid);
        if (entity!=null) {
            entity.setStatus(Status.ACTIVE);
            userRepository.save(entity);
            return true;
        }
        return false;
    }

    public MessageDTO edit(Long sessionUserId, UserEditRegDTO userEditRegDTO) {
        UserEntity entity = userRepository.getById(sessionUserId);
        entity.setName(userEditRegDTO.getName());
        if ((!entity.getSign().equals(userEditRegDTO.getSign())) && userRepository.findBySign(userEditRegDTO.getSign())!=null) {
            return MessageDTO.failed("Логин уже занят, попробуйте другой");
        }
        entity.setSign(userEditRegDTO.getSign());
        if ((!entity.getMail().equals(userEditRegDTO.getMail())) && userRepository.findByEmail(userEditRegDTO.getMail())!=null)
        {
            return MessageDTO.failed("Электронная почта уже занята");
        }
        if (!entity.getMail().equals(userEditRegDTO.getMail())) {
            eventPublisher.publishEvent(new UserRegisteredPublished(entity.getSign(), entity.getUuid(), entity.getMail()));
        }
        entity.setMail(userEditRegDTO.getMail());
        userRepository.save(entity);
        return MessageDTO.succeed("Успех");
    }

    public MessageDTO edit_pass(Long sessionUserId, UserEditPassDTO userEditPassDTO) {
        UserEntity entity = userRepository.getById(sessionUserId);
        if (!passwordEncoder.matches(userEditPassDTO.getOldPass(),entity.getPass())) {
            return MessageDTO.failed("Старый пароль не совпадает");
        }
        if (passwordEncoder.matches(userEditPassDTO.getNewPass(),entity.getPass())) {
            return MessageDTO.failed("Старый и новый пароли не должны совпадать");
        }
        entity.setPass(passwordEncoder.encode(userEditPassDTO.getNewPass()));
        userRepository.save(entity);
        return MessageDTO.succeed("Успех");
    }

    public UserEditRegDTO getProfile(Long sessionUserId) {
        UserEntity entity = userRepository.getById(sessionUserId);
        return entityDtoMapper.toUserEditRegDTO(entity);
    }

    public MessageDTO uploadNewAvatar(MultipartFile file, Long userId) throws IOException {

        if (file.getSize() > 100 * 2014) {
            return MessageDTO.failed("Файл размером " + file.getSize() + " байт! максимальный размер 100 кб");
        }
        if (!dirUtils.isImage(file.getOriginalFilename())) {
            return MessageDTO.failed("Не поддерживаемый формат");
        }
        String avatarName = "avatar-" + System.currentTimeMillis() + "." + dirUtils.getFileExt(file.getOriginalFilename());
        Path path = Paths.get(dirUtils.getUserDir(),String.valueOf(userId), "avatar");
        Files.createDirectories(path);
        path = Paths.get(String.valueOf(path), avatarName);
        Files.copy(file.getInputStream(), path);

        UserEntity entity = userRepository.getById(userId);
        entity.setAvatar(String.valueOf(path.getFileName()));
        userRepository.save(entity);

        return MessageDTO.succeed("Аватар обновлен");
    }

    //----------------------------------------- DEBUG METHODS

    // загрузка по ссылке
//    public MessageDTO saveImg(Long id,String path) throws IOException {
//        URL url = new URL(path);
//        String name = "avatar-" + System.currentTimeMillis() + "." + dirUtils.getFileExt(url.getFile());
//        File file = new File("img/" + name);
//
//        FileUtils.copyURLToFile(url, file);
//        UserEntity entity = userRepository.getById(id);
//        entity.setAvatar(name);
//        userRepository.save(entity);
//        return MessageDTO.builder().success(true).message("наверное получилось").build();
//    }

    // достает всех из базы
    public List<UserRegDTO> show() {
        List<UserRegDTO> res = new ArrayList<>();
        userRepository.findAll().forEach(userEntity -> res.add(UserRegDTO.builder()
                .name(userEntity.getName())
                .sign(userEntity.getSign())
                .pass(userEntity.getPass())
                .mail(userEntity.getMail())
                .build()
        ));
        return res;
    }
    // добавляет тестового пользователя
    public void testUser() {
        UserEntity entity = new UserEntity();
        entity.setName("asdf");
        entity.setSign("ASDF");
        entity.setPass(passwordEncoder.encode("ASDASD"));
        entity.setMail("lions.tech.email@mail.ru");
        entity.setUuid(UUID.randomUUID().toString());
        entity.setStatus(Status.ACTIVE);
        userRepository.save(entity);
    }


}
