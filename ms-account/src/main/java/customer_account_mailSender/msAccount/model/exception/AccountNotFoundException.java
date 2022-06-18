package customer_account_mailSender.msAccount.model.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super(String.format("Account with id %d not found", id));
    }

    public AccountNotFoundException(String accountNo) {
        super(String.format("Account %s not found", accountNo));
    }
}