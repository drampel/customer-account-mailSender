package customer_account_mailSender.msCustomer.model.exception;

public class CustomerWithEmailExistException extends RuntimeException {
    public CustomerWithEmailExistException(String email) {
        super(String.format("Customer with email address %s already exists", email));
    }
}