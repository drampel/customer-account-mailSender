package customer_account_mailSender.msCustomer.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = BirthdateValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface BirthdateConstraint {
    String message() default "Invalid date of birth.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}