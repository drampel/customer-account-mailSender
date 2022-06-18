package customer_account_mailSender.msMailSender.service;

import customer_account_mailSender.msMailSender.model.MailDto;

public interface EmailService {
    void sendSimpleMessage(MailDto mailDto);
}