package customer_account_mailSender.msAccount.dao;

import customer_account_mailSender.msAccount.model.Currency;
import customer_account_mailSender.msAccount.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static customer_account_mailSender.msAccount.model.Status.ACTIVE;
import static customer_account_mailSender.msAccount.model.Status.PASSIVE;
import static java.math.BigDecimal.ZERO;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "account")
@EntityListeners(AccountListener.class)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "status != 'DELETED'")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long customerId;
    private String accountNo;
    private BigDecimal amount;
    @Enumerated(STRING)
    private Currency currency;
    @Enumerated(STRING)
    private Status status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    AccountHistoryEntity toAccountHistoryEntity() {
        return AccountHistoryEntity.builder()
                .account(this)
                .customerId(this.customerId)
                .accountNo(this.accountNo)
                .amount(this.amount)
                .currency(this.currency)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    @PrePersist
    void init() {
        if (this.amount == null || this.amount.compareTo(ZERO) == 0) {
            this.amount = ZERO;
            this.status = PASSIVE;
        } else this.status = ACTIVE;
    }
}