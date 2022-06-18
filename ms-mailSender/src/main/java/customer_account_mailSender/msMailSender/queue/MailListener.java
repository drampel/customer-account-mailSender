package customer_account_mailSender.msMailSender.queue;

import customer_account_mailSender.msMailSender.model.MailDto;
import customer_account_mailSender.msMailSender.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailListener {
    private final EmailService emailService;

    public MailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${queue.mail-sender-q}")
    public void getMessage(MailDto mailDto) {
        emailService.sendSimpleMessage(mailDto);
        log.info("Message is: " + mailDto);
    }
}