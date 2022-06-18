package customer_account_mailSender.msAccount.util;

import customer_account_mailSender.msAccount.dao.AccountEntity;
import customer_account_mailSender.msAccount.dao.AccountRepository;
import customer_account_mailSender.msAccount.model.exception.AccountNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountUtil {
    private final AccountRepository accountRepository;

    public AccountUtil(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountEntity accountExistById(Long id) {
        Optional<AccountEntity> entityOptional = accountRepository.findById(id);
        if (entityOptional.isEmpty()) throw new AccountNotFoundException(id);
        return entityOptional.get();
    }
}