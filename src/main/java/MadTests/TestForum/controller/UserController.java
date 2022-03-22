package MadTests.TestForum.controller;

import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping(value = "/users")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "startPage";
    }

    @GetMapping("/registration")
    public String reg() {
        return "registration";
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

}