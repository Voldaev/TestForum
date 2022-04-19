package MadTests.TestForum.controller.rest;

import MadTests.TestForum.controller.BaseController;
import MadTests.TestForum.dto.InputDTO;
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
                                @RequestBody InputDTO theme)
    {
        return contentService.createTheme(section, theme.getDesc(), theme.getText(), getSessionUserId());
    }

    @PostMapping("/{theme}/comment")
    public MessageDTO makeComm(@PathVariable String theme, @RequestBody InputDTO comm)
    {
        return contentService.createComment(theme, comm.getText(), getSessionUserId());
    }

    @PostMapping("/vote/theme/{id}")
    public MessageDTO voteTheme(@PathVariable Long id)
    {
        return contentService.voteTheme(id,getSessionUserId());
    }

    @PostMapping("/vote/comment/{id}")
    public MessageDTO voteComm(@PathVariable Long id)
    {
        return contentService.voteComm(id,getSessionUserId());
    }


}
