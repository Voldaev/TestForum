package MadTests.TestForum.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegisteredPublished {

    private String sign;
    private String uuid;
    private String mail;

}

