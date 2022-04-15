package MadTests.TestForum.controller.rest;

import MadTests.TestForum.dto.MessageDTO;
import MadTests.TestForum.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class ContentController {

    @Autowired
    ContentService contentService;
    
    @PostMapping("/{section}/create")
    public MessageDTO makeTheme(){
        return null;
    }

    @PostMapping("/{section}/{theme}/comment")
    public MessageDTO makeComm(){
        return null;
    }


}
