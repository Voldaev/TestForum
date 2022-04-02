package MadTests.TestForum.service;

import MadTests.TestForum.config.DirUtils;
import MadTests.TestForum.dto.*;
import MadTests.TestForum.event.UserRegisteredPublished;
import MadTests.TestForum.mapper.UserMapper;
import MadTests.TestForum.model.UserEntity;
import MadTests.TestForum.rep.UserRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

    private void setSessionUserId(Long userId) {
        if (userId == null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        } else {
            List<GrantedAuthority> roles = new ArrayList<>(0);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,null, roles);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private MessageDTO message(boolean b, String text) {
        return MessageDTO.builder()
                .success(b)
                .message(text)
                .build();
    }

    public MessageDTO save(UserRegDTO user) {
        UserEntity entity = userRepository.findByEmail(user.getMail());
        if (entity != null) {
            return message(false, "Электронная почта уже занята");
        }

        entity = new UserEntity();
        if (user.getName().length()<2) {
            return message(false, "Некорректное имя");
        }
        entity.setName(user.getName());

        if (userRepository.findBySign(user.getSign())!=null) {
            return message(false, "Логин уже занят, попробуйте другой");
        }
        if (user.getName().length()<3) {
            return message(false, "Слишком короткий логин");
        }
        entity.setSign(user.getSign());
        if (user.getPass().length()<6) {
            return message(false, "Слишком короткий пароль");
        }
        entity.setPass(passwordEncoder.encode(user.getPass()));
        Pattern pattern = Pattern.compile("([A-Za-z0-9]+[\\\\-]?[A-Za-z0-9]+[\\\\.]?[A-Za-z0-9]+)+@([A-Za-z0-9]+[\\\\-]?[A-Za-z0-9]+[\\\\.]?[A-Za-z0-9]+)+[\\\\.][a-z]{2,4}");
        Matcher matcher = pattern.matcher(user.getMail());
        if (!matcher.matches()) {
            return message(false, "Некорректный email");
        }
        entity.setMail(user.getMail());
        entity.setStatus(0);
        entity.setUuid(UUID.randomUUID().toString());
        userRepository.save(entity);

        eventPublisher.publishEvent(new UserRegisteredPublished(entity.getSign(), entity.getUuid(), entity.getMail()));
        return message(true, "Успех");
    }

    public MessageDTO check(LoginDTO l) {
        //TODO ниже не хватает проверки что пользователь вообще есть в системе. У тебя тут вылетит ошибка. Можешь проверить.
        if (passwordEncoder.matches(l.getPass(), userRepository.findBySign(l.getSign()).getPass())) {
            setSessionUserId(userRepository.findBySign(l.getSign()).getId());
            return MessageDTO.builder().success(true).message("подтверждено").build();
        } else {
            return MessageDTO.builder().success(false).message("данные не верны").build();
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
            return message(false, "Некорректное имя");
        }
        entity.setName(userEditRegDTO.getName());

        if ((!entity.getSign().equals(userEditRegDTO.getSign())) && userRepository.findBySign(userEditRegDTO.getSign())!=null) {
            return message(false, "Логин уже занят, попробуйте другой");
        }
        if (userEditRegDTO.getName().length()<3) {
            return message(false, "Слишком короткий логин");
        }
        entity.setSign(userEditRegDTO.getSign());

        if ((!entity.getMail().equals(userEditRegDTO.getMail())) && userRepository.findByEmail(userEditRegDTO.getMail())!=null)
        {
            return message(false, "Электронная почта уже занята");
        }
        Pattern pattern = Pattern.compile("([A-Za-z0-9]+[\\\\-]?[A-Za-z0-9]+[\\\\.]?[A-Za-z0-9]+)+@([A-Za-z0-9]+[\\\\-]?[A-Za-z0-9]+[\\\\.]?[A-Za-z0-9]+)+[\\\\.][a-z]{2,4}");
        Matcher matcher = pattern.matcher(userEditRegDTO.getMail());
        if (!matcher.matches()) {
            return message(false, "некорректный email");
        }
        if (!entity.getMail().equals(userEditRegDTO.getMail())) {
            eventPublisher.publishEvent(new UserRegisteredPublished(entity.getSign(), entity.getUuid(), entity.getMail()));
        }
        entity.setMail(userEditRegDTO.getMail());
        userRepository.save(entity);
        return message(true, "успех");
    }

    public MessageDTO edit_pass(Long sessionUserId, UserEditPassDTO userEditPassDTO) {
        UserEntity entity = userRepository.getById(sessionUserId);
        if (!passwordEncoder.matches(userEditPassDTO.getOldPass(),entity.getPass())) {
            return message(false, "старый пароль не совпадает");
        }
        if (passwordEncoder.matches(userEditPassDTO.getNewPass(),entity.getPass())) {
            return message(false, "старый и новый пароли не должны совпадать");
        }
        if (userEditPassDTO.getNewPass().length()<6) {
            return message(false, "новый пароль слишком короткий");
        }
        entity.setPass(passwordEncoder.encode(userEditPassDTO.getNewPass()));
        userRepository.save(entity);
        return message(true, "успех");
    }

    @Autowired
    UserMapper userMapper;

    public UserEditRegDTO getProfile(Long sessionUserId) {
        UserEntity entity = userRepository.getById(sessionUserId);
        return userMapper.userEditRegDTO(entity);
    }

    //----------------------------------------- DEBUG METHODS

    public MessageDTO saveImg(Long id,String path) throws IOException {
        URL url = new URL(path);
        String name =  UUID.randomUUID() + "_avatar.jpg";
        File file = new File( "img/" + name);
        FileUtils.copyURLToFile(url, file); //fixme тут надо бы перехватить эксепшн
        UserEntity entity = userRepository.getById(id);
        entity.setAvatar(name);
        userRepository.save(entity);
        return MessageDTO.builder().success(true).message("наверное получилось").build();
    }

    @Autowired
    DirUtils dirUtils;

    public MessageDTO uploadNewAvatar(MultipartFile file) throws IOException {
        if (file.getSize() > 100 * 2014) {
            return MessageDTO.ofFalse("Какие еще " + file.getSize() + " байт. Меньше давай");
        }
        if (!isImage(file.getName())) {
            return MessageDTO.ofFalse("Не поддерживаемый формат");
        }
        String avatarName = "avatar-" + System.currentTimeMillis() + "." + getFileExt(file.getName());
        Path path = Paths.get(dirUtils.getUserDir(),String.valueOf(1), "avatar", avatarName); //todo тут вместо единицы идентификатор пользователя из сессии
        Files.copy(file.getInputStream(), path);
        //todo тут привязку к юзеру далее
        return MessageDTO.ofTrue("Аватар обновлен");
    }

    public static String getFileExt(final String filename) { //todo перенести в класс ютилит и нижний тоже
        String ext = null;
        if (filename != null && filename.contains(".")) {
            int i = filename.lastIndexOf(".");
            ext = filename.substring(i + 1);
        }
        ext = StringUtils.trimToNull(ext);
        ext = StringUtils.lowerCase(ext);
        return ext;
    }

    public static boolean isImage(final String filename) {
        String ext = getFileExt(filename);
        if (ext == null) {
            return false;
        }
        switch (ext) {
            case "jpg":
            case "jpeg":
            case "png":
                return true;
        }
        return false;
    }


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
