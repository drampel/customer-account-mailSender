package customer_account_mailSender.msAccount.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = AccountNoValidation.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface AccountNoConstraint {
    String message() default "Invalid account number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}