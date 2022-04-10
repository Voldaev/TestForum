package MadTests.TestForum.controller.rest;

import MadTests.TestForum.controller.BaseController;
import MadTests.TestForum.dto.LoginDTO;
import MadTests.TestForum.dto.MessageDTO;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController extends BaseController {

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public MessageDTO reg(@RequestBody @Valid UserRegDTO userRegDTO) {
        //fixme перехват экспешенов валидации
        return userService.save(userRegDTO);
    }

    @PostMapping("/login")
    public MessageDTO login(@RequestBody LoginDTO loginDTO) {
        return userService.check(loginDTO);
    }

}
