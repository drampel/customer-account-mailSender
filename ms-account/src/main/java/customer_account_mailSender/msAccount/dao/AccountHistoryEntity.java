package customer_account_mailSender.msAccount.dao;

import customer_account_mailSender.msAccount.model.Currency;
import customer_account_mailSender.msAccount.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "account_history")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountHistoryEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private AccountEntity account;
    private Long customerId;
    private String accountNo;
    private BigDecimal amount;
    @Enumerated(STRING)
    private Currency currency;
    @Enumerated(STRING)
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}