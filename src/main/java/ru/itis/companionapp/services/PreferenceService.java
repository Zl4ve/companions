package ru.itis.companionapp.services;

import ru.itis.companionapp.dto.forms.PreferenceForm;
import ru.itis.companionapp.models.Preference;
import ru.itis.companionapp.models.User;

public interface PreferenceService {
    Preference getByText(String preference);
    void addPreference(PreferenceForm preferenceForm, User user);
}
