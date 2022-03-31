package MadTests.TestForum.controller.rest;

import MadTests.TestForum.dto.*;
import MadTests.TestForum.service.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public MessageDTO reg(@RequestBody @Valid UserRegDTO userRegDTO) {
        return userService.save(userRegDTO);
    }

    @PostMapping("/login")
    public MessageDTO login(@RequestBody LoginDTO loginDTO) {
        return userService.check(loginDTO);
    }

}
