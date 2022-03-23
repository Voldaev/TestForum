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

    @GetMapping("/main")
    public String hello(Model model) {
        model.addAttribute("name", userService.getName(getSessionUserId()));
        if (userService.getStatus(getSessionUserId())>0) {
            model.addAttribute("status", "учетная запись подтверждена"); }
        else {
            model.addAttribute("status","Внимание! email не подтвержден!");
        }
        return "main";
    }

    @GetMapping("/main/confirmation")
    public String confirm() {
        return "confirmation";
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