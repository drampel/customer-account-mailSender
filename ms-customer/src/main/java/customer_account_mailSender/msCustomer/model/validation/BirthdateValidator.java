package customer_account_mailSender.msCustomer.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class BirthdateValidator implements ConstraintValidator<BirthdateConstraint, LocalDate> {
    @Override
    public void initialize(BirthdateConstraint constraint) {
    }

    @Override
    public boolean isValid(LocalDate birthdateField, ConstraintValidatorContext cxt) {
        return birthdateField == null ||
                (Period.between(birthdateField, LocalDate.now()).getYears() >= 18 &&
                        Period.between(birthdateField, LocalDate.now()).getYears() <= 150);
    }
}