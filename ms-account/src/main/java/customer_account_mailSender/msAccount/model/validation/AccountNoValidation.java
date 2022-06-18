package customer_account_mailSender.msAccount.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountNoValidation implements ConstraintValidator<AccountNoConstraint, String> {
    @Override
    public void initialize(AccountNoConstraint constraint) {
    }

    @Override
    public boolean isValid(String accountNoField, ConstraintValidatorContext cxt) {
        return accountNoField != null &&
                accountNoField.matches("^\\d{5}[a-zA-Z]{3}\\d{12}$") &&
                (accountNoField.length() == 20);
    }
}