package ru.itis.companionapp.utils.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import ru.itis.companionapp.exceptions.ReviewNotFoundException;
import ru.itis.companionapp.models.Drive;
import ru.itis.companionapp.models.Review;
import ru.itis.companionapp.services.ReviewService;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class ReviewValidator {

    private final ReviewService reviewService;

    public String validate(Review review, Drive drive) {
        if (review.getAuthor().equals(drive.getDriver())) {
            return "You was a driver in this drive";
        } else if (!drive.getCompanions().contains(review.getAuthor())) {
            return "You weren't on this drive";
        } else if (!drive.getDate().isBefore(LocalDate.now())) {
            return "This drive isn't over yet";
        } else {
            try {
                reviewService.getByAuthorIdAndAccoundId(review.getAuthor().getId(), drive.getDriver().getId());
                return "You have already reviewed this user";
            } catch (ReviewNotFoundException ignored) {}
        }

        return null;
    }
}
