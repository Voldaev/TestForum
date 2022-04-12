package MadTests.TestForum.controller;

import MadTests.TestForum.dto.UserEditRegDTO;
import MadTests.TestForum.model.enums.Status;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController extends BaseController {

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

    @GetMapping("/confirmation/{uuid}")
    public String confirm(Model model, @PathVariable String uuid) {
        if (userService.active(uuid)) {
            model.addAttribute("message","электронная почта подтверждена!");
        } else {
            model.addAttribute("message", "пользователь не найден!");
        }
        return "confirmation";
    }

    @GetMapping("/login")
    public String login() {
        return "login_page";
    }

    @GetMapping("/main")
    public String hello(Model model) {
        model.addAttribute("name", userService.getName(getSessionUserId()));
        if (userService.getStatus(getSessionUserId()).equals(Status.UNCHECKED)) {
            model.addAttribute("status","Внимание! email не подтвержден!");
        }
        return "main";
    }

    @GetMapping("/main/profile")
    public String profile(Model model) {
        UserEditRegDTO profile = userService.getProfile(getSessionUserId());
        if (profile.getAvatar() == null) {
            model.addAttribute("useravatar","/static/img/default.jpg");
        } else {
            model.addAttribute("useravatar", "/main/profile/avatar/" + profile.getAvatar());
        }
        model.addAttribute("username", profile.getName());
        model.addAttribute("userlogin",profile.getSign());
        model.addAttribute("usermail", profile.getMail());
        return "profile";
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