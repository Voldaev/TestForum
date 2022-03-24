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

    @PostMapping("/edit")
    public MessageDTO edit(@RequestBody UserEditRegDTO userEditRegDTO) {
        return userService.edit(getSessionUserId(),userEditRegDTO);
    }

    @PostMapping("/edit_pass")
    public MessageDTO edit_pass(@RequestBody UserEditPassDTO userEditPassDTO) {
        return userService.edit_pass(getSessionUserId(), userEditPassDTO);
    }

    private Long getSessionUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            Object o = token.getPrincipal();
            if (o instanceof Long) {
                return (Long) o;
            }
        }
        return null;
    }
}
