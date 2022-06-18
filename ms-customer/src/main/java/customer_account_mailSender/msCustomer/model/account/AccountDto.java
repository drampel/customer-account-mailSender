package customer_account_mailSender.msCustomer.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    private Long customerId;
    private String accountNo;
}