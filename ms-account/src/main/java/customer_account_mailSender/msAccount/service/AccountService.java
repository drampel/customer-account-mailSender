package customer_account_mailSender.msAccount.service;

import customer_account_mailSender.msAccount.model.AccountDto;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAccounts(Long customerId);

    AccountDto getAccountByID(Long id);

    AccountDto getAccountByAccountNo(String accountNo);

    AccountDto createAccount(AccountDto dto);

    AccountDto updateAccountById(AccountDto dto);

    AccountDto updateAccountByAccountNo(AccountDto dto);

    AccountDto deleteAccountByID(Long id);

    AccountDto deleteAccountByAccountNo(String accountNo);
}