package MadTests.TestForum.controller.rest;

import MadTests.TestForum.config.DirUtils;
import MadTests.TestForum.controller.BaseController;
import MadTests.TestForum.dto.MessageDTO;
import MadTests.TestForum.dto.UserEditPassDTO;
import MadTests.TestForum.dto.UserEditRegDTO;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/main/profile")
public class ProfileController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
    DirUtils dirUtils;

    @GetMapping("/avatar/{filename}")
    public void getImage(HttpServletResponse response, @PathVariable String filename) throws IOException {
        Path path = Paths.get(dirUtils.getUserDir(), getSessionUserId().toString(), "avatar", filename);
        FileInputStream stream = new FileInputStream(path.toFile());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(stream, response.getOutputStream());
    }

    //вариант мультипарт на вход для формы
    @PostMapping(value = "/avatar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageDTO saveFile(@RequestBody MultipartFile file) throws IOException {
        return userService.uploadNewAvatar(file, getSessionUserId());
    }
//    // приём загрузки по url
//    @PostMapping("/img/save")
//    public MessageDTO saveImage(@RequestBody Map.Entry<String,String> url) throws IOException {
//        return userService.saveImg(getSessionUserId(), url.getValue());
//    }


    @PostMapping("/edit")
    public MessageDTO edit(@Valid @RequestBody UserEditRegDTO userEditRegDTO) {

        return userService.edit(getSessionUserId(),userEditRegDTO);
    }

    @PostMapping("/edit_pass")
    public MessageDTO edit_pass(@Valid @RequestBody UserEditPassDTO userEditPassDTO) {

        return userService.edit_pass(getSessionUserId(), userEditPassDTO);
    }
}
