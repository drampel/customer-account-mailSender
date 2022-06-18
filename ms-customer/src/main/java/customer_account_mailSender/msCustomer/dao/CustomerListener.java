package customer_account_mailSender.msCustomer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Component
public class CustomerListener {
    private static CustomerHistoryRepository customerHistoryRepository;

    @Autowired
    public void init(CustomerHistoryRepository customerHistoryRepository) {
        CustomerListener.customerHistoryRepository = customerHistoryRepository;
    }

    @PostUpdate
    @PostPersist
    public void saveCustomerChanges(CustomerEntity entity) {
        customerHistoryRepository.save(entity.toCustomerHistoryEntity());
    }
}