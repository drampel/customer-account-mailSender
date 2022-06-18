package customer_account_mailSender.msAccount.model.validation;

import customer_account_mailSender.msAccount.model.Currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CurrencyValidator implements ConstraintValidator<CurrencyConstraint, Currency> {
    private Currency[] currencies;

    @Override
    public void initialize(CurrencyConstraint constraint) {
        this.currencies = constraint.anyOf();
    }

    @Override
    public boolean isValid(Currency currencyField, ConstraintValidatorContext context) {
        return currencyField == null || Arrays.asList(currencies).contains(currencyField);
    }
}