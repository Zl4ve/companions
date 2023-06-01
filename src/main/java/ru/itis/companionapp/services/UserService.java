package ru.itis.companionapp.services;

import ru.itis.companionapp.dto.forms.UserForm;
import ru.itis.companionapp.models.User;

public interface UserService {
    void signUp(UserForm userForm);
    User getByUsername(String username);
    User getByPhoneNumber(String phoneNumber);
    void updateUserAvatar(String username, String avatarRef);
    void updateUserCarInfo(String username, String brand, String model);
    User getById(Long id);
    void updateRating(User account);
    void ban(User user);
}
