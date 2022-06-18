package customer_account_mailSender.msCustomer.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameSurnameValidator implements ConstraintValidator<NameSurnameConstraint, String> {
    @Override
    public void initialize(NameSurnameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String nameSurnameField, ConstraintValidatorContext cxt) {
        return nameSurnameField == null ||
                nameSurnameField.matches("^\\S+$") &&
                        nameSurnameField.length() >= 2 &&
                        nameSurnameField.length() <= 16;
    }
}