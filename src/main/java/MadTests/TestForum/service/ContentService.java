package MadTests.TestForum.service;

import MadTests.TestForum.rep.CommentRepository;
import MadTests.TestForum.rep.SectionRepository;
import MadTests.TestForum.rep.ThemeRepository;
import MadTests.TestForum.rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ContentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<String> getSectionNames() {
        ArrayList<String> list = new ArrayList<>();
        sectionRepository.findAll().forEach(section -> {
            list.add(section.getName());
        });
        return list;
    }

}
