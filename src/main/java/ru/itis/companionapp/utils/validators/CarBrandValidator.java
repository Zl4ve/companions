package ru.itis.companionapp.utils.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CarBrandValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        String brand = (String) target;

        if (brand.equals("")) {
            errors.rejectValue("brand", "", "Enter the brand");
        }
    }
}
