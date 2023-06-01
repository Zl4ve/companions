package ru.itis.companionapp.utils.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itis.companionapp.dto.forms.PreferenceForm;
import ru.itis.companionapp.exceptions.PreferenceNotFoundException;
import ru.itis.companionapp.services.PreferenceService;

@RequiredArgsConstructor
@Component
public class PreferenceValidator implements Validator {

    private final PreferenceService preferenceService;

    @Override
    public boolean supports(Class<?> clazz) {
        return PreferenceForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        PreferenceForm preferenceForm = (PreferenceForm) target;

        try {
            preferenceService.getByText(preferenceForm.getPreference());
            errors.rejectValue("preference", "", "This preference is exists");
        } catch (PreferenceNotFoundException e) {
        }
    }
}
