package MadTests.TestForum.service;

import MadTests.TestForum.dto.MessageDto;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.model.UserEntity;
import MadTests.TestForum.rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public MessageDto save(UserRegDTO user) {
        UserEntity entity = userRepository.findByEmail(user.getMail());
        if (entity != null) {
            return MessageDto.builder()
                    .success(false)
                    .message("Электронная почта используется другим пользователем")
                    .build();
        }
        entity = new UserEntity();
        entity.setName(user.getLogin());
        entity.setSign(user.getSign());
        entity.setPass(user.getPass());
        entity.setMail(user.getMail());
        entity.setStatus(0);
        userRepository.save(entity);

        return MessageDto.builder()
                .success(true)
                .message("Пользователь создан. Можете заходить на сайт")
                .build();
    }
    //fixme debug method
    public List<UserRegDTO> show() {
        List<UserRegDTO> res = new ArrayList<>();
        userRepository.findAll().forEach(userEntity -> res.add(UserRegDTO.builder()
                        .login(userEntity.getName())
                        .sign(userEntity.getSign())
                        .pass(userEntity.getPass())
                        .mail(userEntity.getMail())
                        .build()
        ));
        return res;
    }

}
