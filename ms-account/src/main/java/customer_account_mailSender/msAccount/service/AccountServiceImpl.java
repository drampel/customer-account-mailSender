package customer_account_mailSender.msAccount.service;

import customer_account_mailSender.msAccount.client.Customer;
import customer_account_mailSender.msAccount.dao.AccountEntity;
import customer_account_mailSender.msAccount.dao.AccountRepository;
import customer_account_mailSender.msAccount.mapper.AccountMapper;
import customer_account_mailSender.msAccount.model.AccountDto;
import customer_account_mailSender.msAccount.model.customer.CustomerDto;
import customer_account_mailSender.msAccount.model.exception.AccountNotFoundException;
import customer_account_mailSender.msAccount.model.exception.AccountNumberExistException;
import customer_account_mailSender.msAccount.model.mail.MailDto;
import customer_account_mailSender.msAccount.queue.MailSender;
import customer_account_mailSender.msAccount.util.AccountUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static customer_account_mailSender.msAccount.model.Status.DELETED;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final Customer customerClient;
    private final AccountUtil accountUtil;
    private final MailSender mailSender;

    public AccountServiceImpl(AccountRepository accountRepository,
                              Customer customerClient,
                              AccountUtil accountUtil,
                              MailSender mailSender) {
        this.accountRepository = accountRepository;
        this.customerClient = customerClient;
        this.accountUtil = accountUtil;
        this.mailSender = mailSender;
    }

    @Override
    public List<AccountDto> getAccounts(Long customerId) {
        List<AccountDto> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(entity -> {
            CustomerDto customerDto;
            if (customerId != null) {
                customerDto = customerClient.getCustomer(customerId);
                if (entity.getCustomerId().equals(customerId)) {
                    accounts.add(AccountMapper.toDto(entity, customerDto));
                }
            } else {
                customerDto = customerClient.getCustomer(entity.getCustomerId());
                accounts.add(AccountMapper.toDto(entity, customerDto));
            }
        });
        return accounts;
    }

    @Override
    public AccountDto getAccountByID(Long id) {
        AccountEntity entity = accountUtil.accountExistById(id);
        CustomerDto customerDto = customerClient.getCustomer(entity.getCustomerId());
        return AccountMapper.toDto(entity, customerDto);
    }

    @Override
    public AccountDto getAccountByAccountNo(String accountNo) {
        AccountDto existingAccountDto = null;
        for (AccountEntity accountEntity : accountRepository.findAll()) {
            if (accountEntity.getAccountNo().equalsIgnoreCase(accountNo)) {
                CustomerDto customerDto = customerClient.getCustomer(accountEntity.getCustomerId());
                existingAccountDto = AccountMapper.toDto(accountEntity, customerDto);
                break;
            }
        }
        if (existingAccountDto == null) throw new AccountNotFoundException(accountNo.toUpperCase());
        return existingAccountDto;
    }

    @Override
    public AccountDto createAccount(AccountDto dto) {
        CustomerDto customerDto = customerClient.getCustomer(dto.getCustomerId());
        accountRepository.findAll().forEach(accountEntity -> {
            if (accountEntity.getAccountNo().equalsIgnoreCase(dto.getAccountNo()))
                throw new AccountNumberExistException(dto.getAccountNo().toUpperCase());
        });
        AccountEntity entity = accountRepository.save(AccountMapper.toEntity(dto));
        mailSender.sendEmail(new MailDto(customerDto.getEmail(), "Account creation notice",
                String.format("Your account %s has been created. Congratulations!", entity.getAccountNo())));
        return AccountMapper.toDto(entity, customerDto);
    }

    @Override
    public AccountDto updateAccountById(AccountDto dto) {
        AccountEntity entity = accountUtil.accountExistById(dto.getId());
        CustomerDto customerDto = customerClient.getCustomer(entity.getCustomerId());
        AccountMapper.toEntity(dto, entity);
        entity = accountRepository.save(entity);
        mailSender.sendEmail(new MailDto(customerDto.getEmail(), "Account data update notice",
                String.format("Your %s account data have been updated.", entity.getAccountNo())));
        return AccountMapper.toDto(entity, customerDto);
    }

    @Override
    public AccountDto updateAccountByAccountNo(AccountDto dto) {
        AccountDto existingAccountDto = null;
        for (AccountEntity accountEntity : accountRepository.findAll()) {
            if (accountEntity.getAccountNo().equalsIgnoreCase(dto.getAccountNo())) {
                CustomerDto customerDto = customerClient.getCustomer(accountEntity.getCustomerId());
                AccountMapper.toEntity(dto, accountEntity);
                accountRepository.save(accountEntity);
                existingAccountDto = AccountMapper.toDto(accountEntity, customerDto);
                mailSender.sendEmail(new MailDto(customerDto.getEmail(), "Account data update notice",
                        String.format("Your %s account data have been updated.", accountEntity.getAccountNo())));
                break;
            }
        }
        if (existingAccountDto == null) throw new AccountNotFoundException(dto.getAccountNo().toUpperCase());
        return existingAccountDto;
    }

    @Override
    public AccountDto deleteAccountByID(Long id) {
        AccountEntity entity = accountUtil.accountExistById(id);
        CustomerDto customerDto = customerClient.getCustomer(entity.getCustomerId());
        entity.setStatus(DELETED);
        entity = accountRepository.save(entity);
        mailSender.sendEmail(new MailDto(customerDto.getEmail(), "Account deletion notice",
                String.format("Your account %s has been deleted.", entity.getAccountNo())));
        return AccountMapper.toDto(entity, customerDto);
    }

    @Override
    public AccountDto deleteAccountByAccountNo(String accountNo) {
        AccountDto dto = null;
        for (AccountEntity accountEntity : accountRepository.findAll()) {
            if (accountEntity.getAccountNo().equalsIgnoreCase(accountNo)) {
                CustomerDto customerDto = customerClient.getCustomer(accountEntity.getCustomerId());
                accountEntity.setStatus(DELETED);
                accountRepository.save(accountEntity);
                dto = AccountMapper.toDto(accountEntity, customerDto);
                mailSender.sendEmail(new MailDto(customerDto.getEmail(), "Account deletion notice",
                        String.format("Your account %s has been deleted.", accountEntity.getAccountNo())));
                break;
            }
        }
        if (dto == null) throw new AccountNotFoundException(accountNo.toUpperCase());
        return dto;
    }
}