package ru.itis.companionapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.companionapp.dto.forms.PreferenceForm;
import ru.itis.companionapp.exceptions.PreferenceNotFoundException;
import ru.itis.companionapp.models.Preference;
import ru.itis.companionapp.models.User;
import ru.itis.companionapp.repositories.PreferenceRepository;
import ru.itis.companionapp.services.PreferenceService;

@RequiredArgsConstructor
@Service
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceRepository preferenceRepository;

    @Override
    public Preference getByText(String preference) {
        return preferenceRepository.findByText(preference).orElseThrow(() -> new PreferenceNotFoundException("Preference not found"));
    }

    @Override
    public void addPreference(PreferenceForm preferenceForm, User user) {
        Preference pref = Preference.builder()
                .text(preferenceForm.getPreference())
                .account(user)
                .build();
        preferenceRepository.save(pref);
    }
}
