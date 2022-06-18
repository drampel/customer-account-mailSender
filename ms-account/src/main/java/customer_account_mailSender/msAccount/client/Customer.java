package customer_account_mailSender.msAccount.client;

import customer_account_mailSender.msAccount.model.customer.CustomerDto;

public interface Customer {
    CustomerDto getCustomer(Long id);
}