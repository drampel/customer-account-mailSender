package customer_account_mailSender.msCustomer.client;

import customer_account_mailSender.msCustomer.model.account.AccountDto;

import java.util.List;

public interface Account {
    List<AccountDto> getAccountsByCustomerId(Long customerId);

    void deleteAccountById(Long id);
}