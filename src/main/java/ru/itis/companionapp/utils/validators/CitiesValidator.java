package ru.itis.companionapp.utils.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.itis.companionapp.exceptions.CityNotFoundException;
import ru.itis.companionapp.services.CityService;

@RequiredArgsConstructor
@Component
public class CitiesValidator {

    private final CityService cityService;

    public void validate(String source, String destination, Errors errors) {
        try {
            cityService.getByName(source);
        } catch (CityNotFoundException e) {
            errors.rejectValue("source", "", "City not found");
        }

        try {
            cityService.getByName(destination);
        } catch (CityNotFoundException e) {
            errors.rejectValue("destination", "", "City not found");
        }

        if (source.equals(destination)) {
            errors.rejectValue("source", "", "Cities are the same");
        }
    }
}
