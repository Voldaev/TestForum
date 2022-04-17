package MadTests.TestForum.controller.rest;

import MadTests.TestForum.controller.BaseController;
import MadTests.TestForum.dto.LoginDTO;
import MadTests.TestForum.dto.MessageDTO;
import MadTests.TestForum.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
public class ContentController extends BaseController {

    @Autowired
    ContentService contentService;
    
    @PostMapping("/{section}/create")
    public MessageDTO makeTheme(@PathVariable String section,
                                @RequestBody LoginDTO debug) //fixme dto debug
    {
        return contentService.createTheme(section, debug.getSign(), debug.getPass(), getSessionUserId());
    }

    @PostMapping("/{theme}/comment")
    public MessageDTO makeComm(@PathVariable String theme, @RequestBody LoginDTO debug) // fixme dto debug input
    {
        return contentService.createComment(theme, debug.getSign(), getSessionUserId());
    }


}
