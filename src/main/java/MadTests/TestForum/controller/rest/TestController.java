package MadTests.TestForum.controller.rest;

import MadTests.TestForum.dto.AvaDTO;
import MadTests.TestForum.dto.MessageDTO;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/main/img/{filename}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response, @PathVariable String filename) throws IOException {
        var imgFile = new ClassPathResource("img/" + filename);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }

    @PostMapping(value = "/main/img/save")
    public MessageDTO saveImage(@RequestBody AvaDTO avaDTO) throws IOException {
        return userService.saveImg(getSessionUserId(), avaDTO.getUrl());
    }


    @GetMapping(value = "/show")
    public List<UserRegDTO> show(){
        return userService.show();
    }

    @GetMapping(value = "/show/{sign}")
    public UserRegDTO show2(@PathVariable(name = "sign") String sign) {
        return userService.test(sign);
    }

    @GetMapping(value = "/testUser")
    public void testUser() {userService.testUser();}

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
