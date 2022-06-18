package customer_account_mailSender.msAccount.model;

import customer_account_mailSender.msAccount.model.validation.AccountNoConstraint;
import customer_account_mailSender.msAccount.model.validation.AddData;
import customer_account_mailSender.msAccount.model.validation.CurrencyConstraint;
import customer_account_mailSender.msAccount.model.validation.StatusConstraint;
import customer_account_mailSender.msAccount.model.validation.UpdateData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static customer_account_mailSender.msAccount.model.Currency.AZN;
import static customer_account_mailSender.msAccount.model.Currency.CNY;
import static customer_account_mailSender.msAccount.model.Currency.EUR;
import static customer_account_mailSender.msAccount.model.Currency.GBP;
import static customer_account_mailSender.msAccount.model.Currency.INR;
import static customer_account_mailSender.msAccount.model.Currency.JPY;
import static customer_account_mailSender.msAccount.model.Currency.RUB;
import static customer_account_mailSender.msAccount.model.Currency.TRY;
import static customer_account_mailSender.msAccount.model.Currency.UAH;
import static customer_account_mailSender.msAccount.model.Currency.USD;
import static customer_account_mailSender.msAccount.model.Status.ACTIVE;
import static customer_account_mailSender.msAccount.model.Status.BLOCKED;
import static customer_account_mailSender.msAccount.model.Status.PASSIVE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class AccountDto {
    @ApiModelProperty("Unique ID of customer account. This field does not need to be filled in.")
    @Null(message = "ID should not take any values.", groups = {AddData.class, UpdateData.class})
    private Long id;

    @ApiModelProperty(value = "The ID of the customer that owns the account.", example = "11")
    @Positive(message = "Customer ID must be greater than zero.", groups = AddData.class)
    @NotNull(message = "Customer ID is mandatory! Fill in the customer ID.", groups = AddData.class)
    @Null(message = "The customer ID must not take any values.", groups = UpdateData.class)
    private Long customerId;

    @ApiModelProperty(value = "The name of the customer who owns the account. This field does not need to be " +
            "filled in. Automatically taken from customer data.")
    @Null(message = "Customer name should not take any values.", groups = {AddData.class, UpdateData.class})
    private String name;

    @ApiModelProperty(value = "The surname of the customer who owns the account. This field does not need to be " +
            "filled in. Automatically taken from customer data.")
    @Null(message = "Customer surname should not take any values", groups = {AddData.class, UpdateData.class})
    private String surname;

    @ApiModelProperty(value = "Customer account number.", example = "12345AZN123456712345")
    @AccountNoConstraint(message = "The account number is null or entered incorrectly! Example: 12345AZN123456712345.",
            groups = AddData.class)
    @Null(message = "The account number must not take any value in the data update method.", groups = UpdateData.class)
    private String accountNo;

    @ApiModelProperty(value = "The amount of money in the customer's account.", example = "1000.99")
    @PositiveOrZero(message = "The amount must not be negative.", groups = {AddData.class, UpdateData.class})
    @Digits(integer = 12, fraction = 2, message = "Account amount must be less than 1.0E+12. Can only have two " +
            "fractional digits. Example: 1000.99.", groups = {AddData.class, UpdateData.class})
    private BigDecimal amount;

    @ApiModelProperty(value = "Account currency.", example = "AZN")
    @NotNull(message = "Currency is mandatory! Fill in the currency.", groups = AddData.class)
    @Null(message = "The currency should not take any values in the data update method.", groups = UpdateData.class)
    @CurrencyConstraint(anyOf = {AZN, TRY, RUB, UAH, USD, EUR, GBP, CNY, JPY, INR},
            message = "Currency must take the following values: AZN, TRY, RUB, UAH, USD, EUR, GBP, CNY, JPY, INR.",
            groups = AddData.class)
    private Currency currency;

    @ApiModelProperty(value = "Account status (ACTIVE, PASSIVE, BLOCKED, DELETED).", example = "ACTIVE")
    @Null(message = "The status should not take any values in the data creation method.", groups = AddData.class)
    @StatusConstraint(anyOf = {ACTIVE, PASSIVE, BLOCKED}, message = "Status must take the following values: ACTIVE, " +
            "PASSIVE, BLOCKED.", groups = UpdateData.class)
    private Status status;

    @ApiModelProperty("Automatic recording of the creation date of customer account data. This field does not need " +
            "to be filled in.")
    @Null(message = "Creation date should not take any values.", groups = {AddData.class, UpdateData.class})
    private LocalDateTime createdAt;

    @ApiModelProperty("Automatic record of customer account data update date. This field does not need to be filled in.")
    @Null(message = "Update date should not take any values.", groups = {AddData.class, UpdateData.class})
    private LocalDateTime updatedAt;
}