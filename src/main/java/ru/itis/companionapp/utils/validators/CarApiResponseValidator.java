package ru.itis.companionapp.utils.validators;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarApiResponseValidator {
    public String validateJson(String json) {
        if (json == null) {
            return "Something went wrong with connection";
        } else {
            return null;
        }
    }

    public String validateModelList(List<String> models) {
        if (models.isEmpty()) {
            return "There is no info about this brand :(";
        } else {
            return null;
        }
    }
}
