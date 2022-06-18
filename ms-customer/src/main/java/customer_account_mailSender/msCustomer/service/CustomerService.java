package customer_account_mailSender.msCustomer.service;

import customer_account_mailSender.msCustomer.model.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getCustomers();

    CustomerDto getCustomer(Long id);

    CustomerDto addCustomer(CustomerDto dto);

    CustomerDto updateCustomer(CustomerDto dto);

    CustomerDto deleteCustomer(Long id);
}