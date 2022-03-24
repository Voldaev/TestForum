package MadTests.TestForum.event;

import MadTests.TestForum.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener {

    @Autowired
    private MailService mailService;

    @EventListener
    @Async
    public void mailVerify(UserRegisteredPublished event){
        System.out.println(event.getMail()+" отправляем письмо на эту почту!");
        String message = "Вы зарегистрировались как " + event.getSign() + "! вот ссылка для подтверждения email:" +
                " http://localhost:8080/confirmation/" + event.getUuid();
        mailService.sendSimpleMessage(event.getMail(), "Email confirmation", message);

    }

}
