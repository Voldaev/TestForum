package MadTests.TestForum.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener {
    @EventListener
    public void mailVerify(UserRegisteredPublished event) {
        System.out.println(event.getMail()+" отправить письмо на эту почту");
    }

}
