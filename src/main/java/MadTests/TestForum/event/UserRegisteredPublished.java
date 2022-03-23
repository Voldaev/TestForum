package MadTests.TestForum.event;

import lombok.Getter;

@Getter
public class UserRegisteredPublished {

    private String sign;
    private String mail;

    public UserRegisteredPublished(String sign, String mail) {
        this.sign = sign;
        this.mail = mail;
    }
}

