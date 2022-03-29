package MadTests.TestForum.controller.rest;

import MadTests.TestForum.dto.*;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public MessageDTO reg(@RequestBody UserRegDTO userRegDTO) {
        return userService.save(userRegDTO);
    }

    @PostMapping("/login")
    public MessageDTO login(@RequestBody LoginDTO loginDTO) {
        return userService.check(loginDTO);
    }

}
