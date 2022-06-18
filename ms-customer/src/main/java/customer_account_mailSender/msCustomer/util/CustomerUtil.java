package customer_account_mailSender.msCustomer.util;

import customer_account_mailSender.msCustomer.dao.CustomerEntity;
import customer_account_mailSender.msCustomer.dao.CustomerRepository;
import customer_account_mailSender.msCustomer.model.exception.CustomerNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerUtil {
    private final CustomerRepository customerRepository;

    public CustomerUtil(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerEntity customerExist(Long id) {
        Optional<CustomerEntity> entityOptional = customerRepository.findById(id);
        if (entityOptional.isEmpty()) throw new CustomerNotFoundException(id);
        return entityOptional.get();
    }
}