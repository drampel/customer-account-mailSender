package customer_account_mailSender.msCustomer.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    @Query(value = "SELECT * FROM customer AS c WHERE c.email = ? AND c.status != 'DELETED'", nativeQuery = true)
    List<CustomerEntity> findCustomersByEmail(String email);
}