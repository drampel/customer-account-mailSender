package customer_account_mailSender.msAccount.dao;

import org.springframework.data.repository.CrudRepository;

public interface AccountHistoryRepository extends CrudRepository<AccountHistoryEntity, Long> {
}