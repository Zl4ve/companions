package ru.itis.companionapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.companionapp.dto.forms.UserForm;
import ru.itis.companionapp.exceptions.UserNotFoundException;
import ru.itis.companionapp.models.Review;
import ru.itis.companionapp.models.User;
import ru.itis.companionapp.repositories.UserRepository;
import ru.itis.companionapp.services.UserService;
import ru.itis.companionapp.utils.converters.UserFormToUserConverter;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserFormToUserConverter userFormToUserConverter;

    @Override
    public void signUp(UserForm userForm) {
        User user = userFormToUserConverter.convert(userForm);
        userRepository.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User with phone number " + phoneNumber + " not found"));
    }

    @Override
    @Transactional
    public void updateUserAvatar(String username, String avatarRef) {
        userRepository.setAvatarRef(username, avatarRef);
    }

    @Override
    @Transactional
    public void updateUserCarInfo(String username, String brand, String model) {
        userRepository.setCarBrandAndModel(username, brand, model);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void updateRating(User account) {
        if (account.getReviews().size() == 0) {
            account.getDetails().setRating(null);
            return;
        }

        int rateSum = 0;
        for (Review review : account.getReviews()) {
            rateSum += review.getRate();
        }
        double newRating = (double) rateSum / account.getReviews().size();
        account.getDetails().setRating(newRating);
        userRepository.save(account);
    }

    @Override
    public void ban(User user) {
        user.setAccountStatus(User.Status.BANNED);
        userRepository.save(user);
    }
}
