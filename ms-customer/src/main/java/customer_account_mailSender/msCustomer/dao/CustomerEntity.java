package customer_account_mailSender.msCustomer.dao;

import customer_account_mailSender.msCustomer.model.Status;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static customer_account_mailSender.msCustomer.model.Status.ACTIVE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "customer")
@EntityListeners(CustomerListener.class)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "status != 'DELETED'")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String phoneNumber;
    private String email;
    @Enumerated(STRING)
    private Status status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    CustomerHistoryEntity toCustomerHistoryEntity() {
        return CustomerHistoryEntity.builder()
                .customer(this)
                .name(this.name)
                .surname(this.surname)
                .birthdate(this.birthdate)
                .phoneNumber(this.phoneNumber)
                .email(this.email)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    @PrePersist
    void init() {
        this.status = ACTIVE;
    }
}