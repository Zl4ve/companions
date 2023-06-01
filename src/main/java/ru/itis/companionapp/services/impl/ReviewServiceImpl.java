package ru.itis.companionapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.companionapp.exceptions.ReviewNotFoundException;
import ru.itis.companionapp.models.Review;
import ru.itis.companionapp.repositories.ReviewRepository;
import ru.itis.companionapp.services.ReviewService;
import ru.itis.companionapp.services.UserService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserService userService;

    @Override
    public void addReview(Review review) {
        reviewRepository.save(review);
        review.getAccount().addReview(review);
        userService.updateRating(review.getAccount());
    }

    @Override
    public Review getByAuthorIdAndAccoundId(Long authorId, Long accountId) {
        return reviewRepository.findByAuthorIdAndAccountId(authorId, accountId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));
    }

    @Override
    public List<Review> getAllReviewsAboutUser(Long userId) {
        List<Review> reviews = reviewRepository.findAllByAccountId(userId);
        if (reviews.isEmpty()) {
            throw new ReviewNotFoundException("Reviews not found");
        }

        return reviews;
    }

    @Override
    public Review getById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));
    }

    @Override
    public void delete(Review review) {
        reviewRepository.delete(review);
    }

    @Override
    public void update(Review review) {
        reviewRepository.save(review);
    }
}
