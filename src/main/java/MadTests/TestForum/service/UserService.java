package MadTests.TestForum.service;

import MadTests.TestForum.dto.LoginDTO;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.model.UserEntity;
import MadTests.TestForum.rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(UserRegDTO user) {
        UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setSign(user.getSign());
        entity.setPass(passwordEncoder.encode(user.getPass()));
        entity.setMail(user.getMail());
        entity.setStatus(0);
        userRepository.save(entity);
    }
    //fixme debug method
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

    public boolean check(LoginDTO l) {
        return passwordEncoder.matches(userRepository.findBySign(l.getSign()).getPass(), l.getPass());
    }


    public Integer getId(LoginDTO l) {
        return Math.toIntExact(userRepository.findBySign(l.getSign()).getId());
    }

    public UserRegDTO test(String sign) {
        UserEntity entity = userRepository.findBySign(sign);
        return UserRegDTO.builder()
                .name(entity.getName())
                .sign(entity.getSign())
                .pass(entity.getPass())
                .mail(entity.getMail())
                .build();
    }
}
