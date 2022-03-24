package MadTests.TestForum.controller.rest;

import MadTests.TestForum.dto.LoginDTO;
import MadTests.TestForum.dto.MessageDto;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public MessageDto reg(@RequestBody UserRegDTO userRegDTO) {
        return userService.save(userRegDTO);
    }

    @PostMapping("/login")
    public MessageDto login(@RequestBody LoginDTO loginDTO) {
        return userService.check(loginDTO);
    }

}
