package MadTests.TestForum.service;

import MadTests.TestForum.dto.LoginDTO;
import MadTests.TestForum.dto.MessageDto;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.event.UserRegisteredPublished;
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

    public MessageDto save(UserRegDTO user) {
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
            return message(false, "некорректный email");
        }
        entity.setMail(user.getMail());
        entity.setStatus(0);
        entity.setUuid(UUID.randomUUID().toString());
        userRepository.save(entity);

        eventPublisher.publishEvent(new UserRegisteredPublished(entity.getSign(), entity.getUuid(), entity.getMail()));
        return message(true, "успех");
    }

    public MessageDto check(LoginDTO l) {
        if (passwordEncoder.matches(l.getPass(), userRepository.findBySign(l.getSign()).getPass())) {
            setSessionUserId(userRepository.findBySign(l.getSign()).getId());
            return MessageDto.builder().success(true).message("подтверждено").build();
        } else {
            return MessageDto.builder().success(false).message("данные не верны").build();
        }
    }

    private void setSessionUserId(Long userId) {
        if (userId == null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        } else {
            List<GrantedAuthority> roles = new ArrayList<>(0);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,null, roles);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private MessageDto message(boolean b, String text) {
        return MessageDto.builder()
                .success(b)
                .message(text)
                .build();
    }

    public String getName(Long id) {
        return userRepository.getById(id).getName();
    }

    public Integer getStatus(Long id) {
        return userRepository.getById(id).getStatus();
    }

    //----------------------------------------- DEBUG
    // достает пользователя по логину
    public UserRegDTO test(String sign) {
        UserEntity entity = userRepository.findBySign(sign);
        return UserRegDTO.builder()
                .name(entity.getName())
                .sign(entity.getSign())
                .pass(entity.getPass())
                .mail(entity.getMail())
                .build();
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

    public boolean active(String uuid) {
        UserEntity entity = userRepository.findByUuid(uuid);
        if (entity!=null) {
            entity.setStatus(1);
            userRepository.save(entity);
            return true;
        }
        return false;
    }
}
