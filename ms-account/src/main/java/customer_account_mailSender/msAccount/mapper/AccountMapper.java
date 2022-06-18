package customer_account_mailSender.msAccount.mapper;

import customer_account_mailSender.msAccount.dao.AccountEntity;
import customer_account_mailSender.msAccount.model.AccountDto;
import customer_account_mailSender.msAccount.model.customer.CustomerDto;

import java.math.BigDecimal;

import static customer_account_mailSender.msAccount.model.Status.ACTIVE;
import static customer_account_mailSender.msAccount.model.Status.BLOCKED;
import static customer_account_mailSender.msAccount.model.Status.PASSIVE;

public class AccountMapper {

    public static AccountDto toDto(AccountEntity entity, CustomerDto customerDto) {
        return AccountDto.builder()
                .id(entity.getId())
                .customerId(entity.getCustomerId())
                .name(customerDto.getName())
                .surname(customerDto.getSurname())
                .accountNo(entity.getAccountNo().toUpperCase())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static AccountEntity toEntity(AccountDto accountDto) {
        return AccountEntity.builder()
                .customerId(accountDto.getCustomerId())
                .accountNo(accountDto.getAccountNo().toUpperCase())
                .amount(accountDto.getAmount())
                .currency(accountDto.getCurrency())
                .build();
    }

    public static void toEntity(AccountDto accountDto, AccountEntity entity) {
        if (accountDto.getAmount() != null) {
            if (accountDto.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                entity.setAmount(BigDecimal.ZERO);
                entity.setStatus(PASSIVE);
            } else {
                entity.setAmount(accountDto.getAmount());
                entity.setStatus(ACTIVE);
            }
        } else if (accountDto.getStatus() == ACTIVE && entity.getAmount().compareTo(BigDecimal.ZERO) > 0 ||
                accountDto.getStatus() == PASSIVE && entity.getAmount().compareTo(BigDecimal.ZERO) == 0 ||
                accountDto.getStatus() == BLOCKED) entity.setStatus(accountDto.getStatus());
    }
}