package customer_account_mailSender.msCustomer.dao;

import customer_account_mailSender.msCustomer.model.Status;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "customer_history")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerHistoryEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private CustomerEntity customer;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String phoneNumber;
    private String email;
    @Enumerated(STRING)
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}