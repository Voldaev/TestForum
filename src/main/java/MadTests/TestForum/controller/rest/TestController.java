package MadTests.TestForum.controller.rest;

import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
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
    @PostMapping(value = "/main/kek", consumes = MediaType.IMAGE_JPEG_VALUE)
    public String saveFile(MultipartFile file) throws IOException {
        //log.debug("save file name {}", file.getOriginalFilename());
        System.out.println(" мы попали сюда");

        if (file.getSize() > 100 * 2014) {
            System.out.println("ФАЙЛ ЖИРНЫЙ ААААААА");
           //throw new IllegalArgumentException("Какие еще " + file.getSize() + " байт. Меньше давай");
        }

       return userService.saveMultipartFile(file);
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
