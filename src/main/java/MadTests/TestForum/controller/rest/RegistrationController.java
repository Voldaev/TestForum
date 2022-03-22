package MadTests.TestForum.controller.rest;

import MadTests.TestForum.dto.MessageDto;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public MessageDto reg(@RequestBody UserRegDTO userRegDTO) {
        return userService.save(userRegDTO);
    }

}
