package MadTests.TestForum.controller.rest;

import MadTests.TestForum.dto.MessageDTO;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/show")
    public List<UserRegDTO> show(){
        return userService.show();
    }

    @GetMapping(value = "/testUser")
    public void testUser() {userService.testUser();}

    //debug //fixme
    // вариант мультипарт на вход для формы
    @PostMapping(value = "/user/avatar/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageDTO saveFile(@RequestParam("file") MultipartFile file) throws IOException {
        return userService.uploadNewAvatar(file);
    }

//    private Long getSessionUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null) {
//            return null;
//        }
//        if (authentication instanceof UsernamePasswordAuthenticationToken) {
//            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//            Object o = token.getPrincipal();
//            if (o instanceof Long) {
//                return (Long) o;
//            }
//        }
//        return null;
//    }
}
