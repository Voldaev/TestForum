package MadTests.TestForum.event;

import MadTests.TestForum.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRegistrationListener {

    @Autowired
    private MailService mailService;

    @EventListener
    @Async
    public void mailVerify(UserRegisteredPublished event) {
        log.debug("Отправляем письмо на почту {}",event.getMail());
        String message = "Вы зарегистрировались как " + event.getSign() + "! вот ссылка для подтверждения email:" +
                " http://localhost:8080/confirmation/" + event.getUuid();
        mailService.sendSimpleMessage(event.getMail(), "Email confirmation", message);

    }

}
