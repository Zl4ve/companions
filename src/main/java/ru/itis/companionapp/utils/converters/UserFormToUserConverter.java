package ru.itis.companionapp.utils.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.companionapp.dto.forms.UserForm;
import ru.itis.companionapp.models.ProfileDetails;
import ru.itis.companionapp.models.User;

@RequiredArgsConstructor
@Component
public class UserFormToUserConverter implements Converter<UserForm, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User convert(UserForm source) {
        return User.builder()
                .username(source.getUsername())
                .hashPassword(passwordEncoder.encode(source.getPassword()))
                .details(ProfileDetails.builder()
                        .firstName(source.getFirstName())
                        .lastName(source.getLastName())
                        .phoneNumber(source.getPhoneNumber())
                        .build())
                .role(User.Role.USER)
                .accountStatus(User.Status.ACTIVE)
                .build();
    }
}
