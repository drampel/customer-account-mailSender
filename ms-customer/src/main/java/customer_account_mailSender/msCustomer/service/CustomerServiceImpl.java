package customer_account_mailSender.msCustomer.service;

import customer_account_mailSender.msCustomer.client.Account;
import customer_account_mailSender.msCustomer.dao.CustomerEntity;
import customer_account_mailSender.msCustomer.dao.CustomerRepository;
import customer_account_mailSender.msCustomer.mapper.CustomerMapper;
import customer_account_mailSender.msCustomer.model.CustomerDto;
import customer_account_mailSender.msCustomer.model.account.AccountDto;
import customer_account_mailSender.msCustomer.model.exception.CustomerWithEmailExistException;
import customer_account_mailSender.msCustomer.model.mail.MailDto;
import customer_account_mailSender.msCustomer.queue.MailSender;
import customer_account_mailSender.msCustomer.util.CustomerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static customer_account_mailSender.msCustomer.model.Status.DELETED;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final Account accountClient;
    private final CustomerUtil customerUtil;
    private final MailSender mailSender;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               Account accountClient,
                               CustomerUtil customerUtil,
                               MailSender mailSender) {
        this.customerRepository = customerRepository;
        this.accountClient = accountClient;
        this.customerUtil = customerUtil;
        this.mailSender = mailSender;
    }

    @Override
    public List<CustomerDto> getCustomers() {
        List<CustomerDto> customers = new ArrayList<>();
        customerRepository.findAll().forEach(entity -> customers.add(CustomerMapper.toDto(entity)));
        return customers;
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        CustomerEntity entity = customerUtil.customerExist(id);
        return CustomerMapper.toDto(entity);
    }

    @Override
    public CustomerDto addCustomer(CustomerDto dto) {
        customerRepository.findAll().forEach(customerEntity -> {
            if (customerEntity.getEmail().equals(dto.getEmail()))
                throw new CustomerWithEmailExistException(dto.getEmail());
        });
        CustomerEntity entity = customerRepository.save(CustomerMapper.toEntity(dto));
        mailSender.sendEmail(new MailDto(entity.getEmail(), "Notification about adding data to the database",
                "Your data has been successfully added to our database. Congratulations!"));
        return CustomerMapper.toDto(entity);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto dto) {
        CustomerEntity entity = customerUtil.customerExist(dto.getId());
        //TODO If there are multiple customers with the same email addresses that have been manually added or updated
        // using "Submit" in the table
        if (dto.getEmail() != null) {
            List<CustomerEntity> existingCustomers = customerRepository.findCustomersByEmail(dto.getEmail());
            if (!existingCustomers.isEmpty()) {
                CustomerEntity sameCustomer = null;
                for (CustomerEntity customerEntity : existingCustomers) {
                    if (customerEntity.getId().equals(dto.getId())) sameCustomer = customerEntity;
                }
                if (sameCustomer == null) throw new CustomerWithEmailExistException(dto.getEmail());
            }
        }
        CustomerMapper.toEntity(entity, dto);
        entity = customerRepository.save(entity);
        mailSender.sendEmail(new MailDto(entity.getEmail(), "Notification about updating data in the database",
                "Your data has been successfully updated."));
        return CustomerMapper.toDto(entity);
    }

    @Override
    public CustomerDto deleteCustomer(Long id) {
        CustomerEntity entity = customerUtil.customerExist(id);
        entity.setStatus(DELETED);
        List<AccountDto> accounts = accountClient.getAccountsByCustomerId(id);
        accounts.forEach(account -> accountClient.deleteAccountById(account.getId()));
        entity = customerRepository.save(entity);
        log.info(String.format("customer with id %d had accounts: %s", id, accounts));
        mailSender.sendEmail(new MailDto(entity.getEmail(), "Notification about deleting from the database",
                "Your data has been removed from our database."));
        return CustomerMapper.toDto(entity);
    }
}