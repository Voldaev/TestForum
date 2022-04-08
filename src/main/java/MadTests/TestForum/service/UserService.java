package MadTests.TestForum.service;

import MadTests.TestForum.config.DirUtils;
import MadTests.TestForum.dto.*;
import MadTests.TestForum.event.UserRegisteredPublished;
import MadTests.TestForum.mapper.UserMapper;
import MadTests.TestForum.model.UserEntity;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    UserMapper userMapper;

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
        if (true) // fixme debug
            System.out.println("\n" +
                    user.getName() + " - name\n" +
                    user.getSign() + " - sign\n" +
                    user.getPass() + " - pass\n" +
                    user.getMail() + " - mail\n" );
        UserEntity entity = userRepository.findByEmail(user.getMail());
        if (entity != null) {
            return MessageDTO.failed("Электронная почта уже занята");
        }

        entity = new UserEntity();
        if (user.getName().length()<2) {
            return MessageDTO.failed("Некорректное имя");
        }
        entity.setName(user.getName());

        if (userRepository.findBySign(user.getSign())!=null) {
            return MessageDTO.failed("Логин уже занят, попробуйте другой");
        }
        if (user.getName().length()<3) {
            return MessageDTO.failed("Слишком короткий логин");
        }
        entity.setSign(user.getSign());
        if (user.getPass().length()<6) {
            return MessageDTO.failed("Слишком короткий пароль");
        }
        entity.setPass(passwordEncoder.encode(user.getPass()));
        Pattern pattern = Pattern.compile("([A-Za-z0-9]+[\\\\-]?[A-Za-z0-9]+[\\\\.]?[A-Za-z0-9]+)+@([A-Za-z0-9]+[\\\\-]?[A-Za-z0-9]+[\\\\.]?[A-Za-z0-9]+)+[\\\\.][a-z]{2,4}");
        Matcher matcher = pattern.matcher(user.getMail());
        if (!matcher.matches()) {
            return MessageDTO.failed("некорректный email");
        }
        entity.setMail(user.getMail());
        entity.setStatus(0);
        entity.setUuid(UUID.randomUUID().toString());
        userRepository.save(entity);

        eventPublisher.publishEvent(new UserRegisteredPublished(entity.getSign(), entity.getUuid(), entity.getMail()));
        return MessageDTO.succeed("успех");
    }

    public MessageDTO check(LoginDTO l) {
        if (userRepository.findBySign(l.getSign())==null) {
            return MessageDTO.failed("данные не верны");
        }
        if (passwordEncoder.matches(l.getPass(), userRepository.findBySign(l.getSign()).getPass())) {
            setSessionUserId(userRepository.findBySign(l.getSign()).getId());
            return MessageDTO.succeed("подтверждено");
        } else {
            return MessageDTO.failed("данные не верны");
        }
    }

    public String getName(Long id) {
        return userRepository.getById(id).getName();
    }

    public Integer getStatus(Long id) {
        return userRepository.getById(id).getStatus();
    }

    public boolean active(String uuid) {
        UserEntity entity = userRepository.findByUuid(uuid);
        if (entity!=null) {
            entity.setStatus(1);
            userRepository.save(entity);
            return true;
        }
        return false;
    }

    public MessageDTO edit(Long sessionUserId, UserEditRegDTO userEditRegDTO) {
        UserEntity entity = userRepository.getById(sessionUserId);
        if (userEditRegDTO.getName().length()<2) {
            return MessageDTO.failed("Некорректное имя");
        }
        entity.setName(userEditRegDTO.getName());

        if ((!entity.getSign().equals(userEditRegDTO.getSign())) && userRepository.findBySign(userEditRegDTO.getSign())!=null) {
            return MessageDTO.failed("Логин уже занят, попробуйте другой");
        }
        if (userEditRegDTO.getName().length()<3) {
            return MessageDTO.failed("Слишком короткий логин");
        }
        entity.setSign(userEditRegDTO.getSign());

        if ((!entity.getMail().equals(userEditRegDTO.getMail())) && userRepository.findByEmail(userEditRegDTO.getMail())!=null)
        {
            return MessageDTO.failed("Электронная почта уже занята");
        }
        Pattern pattern = Pattern.compile("([A-Za-z0-9]+[\\\\-]?[A-Za-z0-9]+[\\\\.]?[A-Za-z0-9]+)+@([A-Za-z0-9]+[\\\\-]?[A-Za-z0-9]+[\\\\.]?[A-Za-z0-9]+)+[\\\\.][a-z]{2,4}");
        Matcher matcher = pattern.matcher(userEditRegDTO.getMail());
        if (!matcher.matches()) {
            return MessageDTO.failed("некорректный email");
        }
        if (!entity.getMail().equals(userEditRegDTO.getMail())) {
            eventPublisher.publishEvent(new UserRegisteredPublished(entity.getSign(), entity.getUuid(), entity.getMail()));
        }
        entity.setMail(userEditRegDTO.getMail());
        userRepository.save(entity);
        return MessageDTO.succeed("успех");
    }

    public MessageDTO edit_pass(Long sessionUserId, UserEditPassDTO userEditPassDTO) {
        UserEntity entity = userRepository.getById(sessionUserId);
        if (!passwordEncoder.matches(userEditPassDTO.getOldPass(),entity.getPass())) {
            return MessageDTO.failed("старый пароль не совпадает");
        }
        if (passwordEncoder.matches(userEditPassDTO.getNewPass(),entity.getPass())) {
            return MessageDTO.failed("старый и новый пароли не должны совпадать");
        }
        if (userEditPassDTO.getNewPass().length()<6) {
            return MessageDTO.failed("новый пароль слишком короткий");
        }
        entity.setPass(passwordEncoder.encode(userEditPassDTO.getNewPass()));
        userRepository.save(entity);
        return MessageDTO.succeed("успех");
    }

    public UserEditRegDTO getProfile(Long sessionUserId) {
        UserEntity entity = userRepository.getById(sessionUserId);
        return userMapper.toUserEditRegDTO(entity);
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
        entity.setStatus(1);
        userRepository.save(entity);
    }


}
