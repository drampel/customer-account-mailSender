package customer_account_mailSender.msCustomer.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {
    @Override
    public void initialize(PhoneNumberConstraint constraint) {
    }

    @Override
    public boolean isValid(String phoneNumberField, ConstraintValidatorContext cxt) {
        return phoneNumberField == null || (phoneNumberField.matches("^\\+?[0-9]+$") &&
                (phoneNumberField.length() > 8) && (phoneNumberField.length() < 16));
    }
}