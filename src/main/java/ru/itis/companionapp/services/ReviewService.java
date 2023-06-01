package ru.itis.companionapp.services;

import org.springframework.http.ResponseEntity;
import ru.itis.companionapp.models.Review;

import java.util.List;

public interface ReviewService {
    void addReview(Review review);

    Review getByAuthorIdAndAccoundId(Long id, Long id1);

    List<Review> getAllReviewsAboutUser(Long userId);

    Review getById(Long id);

    void delete(Review review);

    void update(Review review);
}
