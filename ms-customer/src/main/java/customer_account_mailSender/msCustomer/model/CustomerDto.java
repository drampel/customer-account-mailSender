package customer_account_mailSender.msCustomer.model;

import customer_account_mailSender.msCustomer.model.validation.AddData;
import customer_account_mailSender.msCustomer.model.validation.BirthdateConstraint;
import customer_account_mailSender.msCustomer.model.validation.NameSurnameConstraint;
import customer_account_mailSender.msCustomer.model.validation.PhoneNumberConstraint;
import customer_account_mailSender.msCustomer.model.validation.StatusConstraint;
import customer_account_mailSender.msCustomer.model.validation.UpdateData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static customer_account_mailSender.msCustomer.model.Status.ACTIVE;
import static customer_account_mailSender.msCustomer.model.Status.BLOCKED;
import static customer_account_mailSender.msCustomer.model.Status.PASSIVE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class CustomerDto {
    @ApiModelProperty("Unique ID of customer. This field does not need to be filled in.")
    @Null(message = "ID should not take any values.", groups = {AddData.class, UpdateData.class})
    private Long id;

    @ApiModelProperty(value = "Customer name.", example = "Jack")
    @NotBlank(message = "The customer name should not be empty, it should not be null, it should not consist " +
            "only of spaces.", groups = AddData.class)
    @NameSurnameConstraint(message = "The customer name must be between 2 and 16 characters long and must not " +
            "contain spaces.", groups = {AddData.class, UpdateData.class})
    private String name;

    @ApiModelProperty(value = "Customer surname.", example = "Johnson")
    @NotBlank(message = "The customer surname should not be empty, it should not be null, it should not consist " +
            "only of spaces.", groups = AddData.class)
    @NameSurnameConstraint(message = "The customer surname must be between 2 and 16 characters long and must not " +
            "contain spaces.", groups = {AddData.class, UpdateData.class})
    private String surname;

    @ApiModelProperty(value = "Customer birthdate.", example = "2000-12-01")
    @NotNull(message = "The customer's date of birth must not be empty, must not be null. " +
            "Correctness of filling: yyyy-mm-dd.", groups = AddData.class)
    @BirthdateConstraint(message = "The date of birth must be in the past. Age must not be less than 18 and over " +
            "150 years old. Correctness of filling: yyyy-mm-dd.", groups = {AddData.class, UpdateData.class})
    private LocalDate birthdate;

    @ApiModelProperty(value = "Customer phone number.", example = "+994501112233")
    @PhoneNumberConstraint(message = "The phone number was entered incorrectly. The phone number can contain " +
            "from 9 to 15 digits with a '+' at the beginning. The phone number can be null.",
            groups = {AddData.class, UpdateData.class})
    private String phoneNumber;

    @ApiModelProperty(value = "Customer email.", example = "jack_johnson@gmail.com")
    @NotBlank(message = "The email should not be empty, it should not be null, it should not consist only of spaces.",
            groups = AddData.class)
    @Email(message = "Email entered incorrectly.", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?" +
            "^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b" +
            "\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:" +
            "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*" +
            "[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\" +
            "x0e-\\x7f])+)\\])", groups = {AddData.class, UpdateData.class})
    private String email;

    @ApiModelProperty(value = "Customer status (ACTIVE, PASSIVE, BLOCKED, DELETED).", example = "ACTIVE")
    @Null(message = "The status should not take any values in the data creation method.", groups = AddData.class)
    @StatusConstraint(anyOf = {ACTIVE, PASSIVE, BLOCKED},
            message = "Status must take the following values: ACTIVE, PASSIVE, BLOCKED.",
            groups = UpdateData.class)
    private Status status;

    @ApiModelProperty("Automatic recording of the creation date of customer data. This field does not need " +
            "to be filled in.")
    @Null(message = "Creation date should not take any values.", groups = {AddData.class, UpdateData.class})
    private LocalDateTime createdAt;

    @ApiModelProperty("Automatic record of customer data update date. This field does not need to be filled in.")
    @Null(message = "Update date should not take any values.", groups = {AddData.class, UpdateData.class})
    private LocalDateTime updatedAt;
}