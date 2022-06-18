package customer_account_mailSender.msAccount.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Component
public class AccountListener {
    private static AccountHistoryRepository accountHistoryRepository;

    @Autowired
    public void init(AccountHistoryRepository accountHistoryRepository) {
        AccountListener.accountHistoryRepository = accountHistoryRepository;
    }

    @PostUpdate
    @PostPersist
    public void saveAccountChanges(AccountEntity entity) {
        accountHistoryRepository.save(entity.toAccountHistoryEntity());
    }
}