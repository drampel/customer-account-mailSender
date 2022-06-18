package customer_account_mailSender.msCustomer.queue;

import customer_account_mailSender.msCustomer.model.mail.MailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailSender {
    private final RabbitTemplate rabbitTemplate;
    private final String mailQ;

    public MailSender(RabbitTemplate rabbitTemplate, @Value("${queue.mail-sender-q}") String mailQ) {
        this.rabbitTemplate = rabbitTemplate;
        this.mailQ = mailQ;
    }

    public void sendEmail(MailDto mailDto) {
        this.rabbitTemplate.convertAndSend(mailQ, mailDto);
    }
}