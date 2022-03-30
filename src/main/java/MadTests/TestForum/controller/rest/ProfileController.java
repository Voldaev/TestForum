package MadTests.TestForum.controller.rest;

import MadTests.TestForum.dto.AvaDTO;
import MadTests.TestForum.dto.MessageDTO;
import MadTests.TestForum.dto.UserEditPassDTO;
import MadTests.TestForum.dto.UserEditRegDTO;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/main/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/img/{filename}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response, @PathVariable String filename) throws IOException {
        File file = new File("img/"+ filename);
        FileInputStream stream = new FileInputStream(file);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(stream, response.getOutputStream());
    }

    @PostMapping("/img/save")
    public MessageDTO saveImage(@RequestBody Map.Entry<String,String> url) throws IOException {
        return userService.saveImg(getSessionUserId(), url.getValue());
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
