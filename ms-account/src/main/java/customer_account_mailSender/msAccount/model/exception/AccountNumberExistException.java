package customer_account_mailSender.msAccount.model.exception;

public class AccountNumberExistException extends RuntimeException {
    public AccountNumberExistException(String accountNo) {
        super(String.format("Account number %s already exists.", accountNo));
    }
}