package ru.itis.companionapp.utils.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface IsDateAndNotPast {
    String message() default "Wrong date";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
