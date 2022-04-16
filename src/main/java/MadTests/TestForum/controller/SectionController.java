package MadTests.TestForum.controller;

import MadTests.TestForum.dto.UserEditRegDTO;
import MadTests.TestForum.model.enums.Status;
import MadTests.TestForum.service.ContentService;
import MadTests.TestForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SectionController extends BaseController{
    @Autowired
    UserService userService;

    @Autowired
    ContentService contentService;

    @GetMapping("/main/{section}/{page}")
    public String section(Model model,
                          @PathVariable String section,
                          @PathVariable int page)
    {
        UserEditRegDTO profile = userService.getProfile(getSessionUserId());
        model.addAttribute("sectionname", section);
        if (profile.getAvatar() == null) {
            model.addAttribute("useravatar","/static/img/default.jpg");
        } else {
            model.addAttribute("useravatar", "/main/profile/avatar/" + profile.getAvatar());
        }
        if (userService.getStatus(getSessionUserId()).equals(Status.UNCHECKED)) {
            model.addAttribute("status","Внимание! email не подтвержден, активность ограничена");
        }
        model.addAttribute("content", contentService.getSectionContent(section, page));

        return "section";
    }
}
