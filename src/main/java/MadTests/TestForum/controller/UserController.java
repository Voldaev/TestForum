package MadTests.TestForum.controller;

import MadTests.TestForum.dto.LoginDTO;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping(value = "/users")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "start_page";
    }

    @GetMapping("/registration")
    public String reg() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login_page";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, @ModelAttribute("person") UserRegDTO user) {

        userService.save(user);
        if (true) //fixme проверки всякие на корректность данных
        {
            return "reg_success";
        } else {
            return "reg_fail";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @ModelAttribute("login_pass") LoginDTO l) {

        if (userService.check(l))
        {
            setSessionUserId(userService.getId(l));
            return "confirmation"; //fixme какая-нибудь интересная страница для авторизованных пользователей
        } else {
            return "wrong_login_data";
        }
    }

    public void setSessionUserId(Integer userId) {
        if (userId == null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        } else {
            List<GrantedAuthority> roles = new ArrayList<>(0);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,null, roles);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    public Integer getSessionUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            Object o = token.getPrincipal();
            if (o instanceof Integer) {
                return (Integer) o;
            }
        }
        return null;
    }



}