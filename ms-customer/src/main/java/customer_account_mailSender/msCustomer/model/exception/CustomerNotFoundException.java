package customer_account_mailSender.msCustomer.model.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super(String.format("Customer with id %d not found", id));
    }
}