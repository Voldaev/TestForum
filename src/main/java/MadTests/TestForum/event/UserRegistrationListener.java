package MadTests.TestForum.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener {

    @EventListener
    @Async
    public void mailVerify(UserRegisteredPublished event) throws InterruptedException {
        System.out.println(event.getMail()+" отправить письмо на эту почту");
    }

}
