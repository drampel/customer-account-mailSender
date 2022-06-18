package customer_account_mailSender.msAccount.model.validation;

import customer_account_mailSender.msAccount.model.Status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class StatusValidator implements ConstraintValidator<StatusConstraint, Status> {
    private Status[] statuses;

    @Override
    public void initialize(StatusConstraint constraint) {
        this.statuses = constraint.anyOf();
    }

    @Override
    public boolean isValid(Status statusField, ConstraintValidatorContext context) {
        return statusField == null || Arrays.asList(statuses).contains(statusField);
    }
}