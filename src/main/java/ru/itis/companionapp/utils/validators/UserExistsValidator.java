package ru.itis.companionapp.utils.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itis.companionapp.dto.forms.UserForm;
import ru.itis.companionapp.exceptions.UserNotFoundException;
import ru.itis.companionapp.services.UserService;

@RequiredArgsConstructor
@Component
public class UserExistsValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm) target;

        try {
            userService.getByUsername(userForm.getUsername());
            errors.rejectValue("username", "", "User with this username is exists");
        } catch (UserNotFoundException e) {
        }

        try {
            userService.getByPhoneNumber(userForm.getPhoneNumber());
            errors.rejectValue("phoneNumber", "", "User with this phone number is exists");
        } catch (UserNotFoundException ex) {
        }
    }
}
