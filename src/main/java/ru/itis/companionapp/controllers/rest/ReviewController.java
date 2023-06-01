package ru.itis.companionapp.controllers.rest;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.itis.companionapp.dto.forms.ReviewForm;
import ru.itis.companionapp.exceptions.ReviewNotByUserException;
import ru.itis.companionapp.exceptions.ReviewNotCreatedException;
import ru.itis.companionapp.exceptions.ReviewUpdateException;
import ru.itis.companionapp.models.Drive;
import ru.itis.companionapp.models.Review;
import ru.itis.companionapp.models.User;
import ru.itis.companionapp.security.details.UserDetailsImpl;
import ru.itis.companionapp.services.DriveService;
import ru.itis.companionapp.services.ReviewService;
import ru.itis.companionapp.services.UserService;
import ru.itis.companionapp.utils.validators.ReviewValidator;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    private final UserService userService;

    private final DriveService driveService;

    private final ReviewValidator reviewValidator;

    @GetMapping (path = "/review/{id}")
    ResponseEntity<Review> getReview(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reviewService.getById(id));
    }

    @ApiOperation(value = "Add review")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfuly added", response = Review.class)
    })
    @PostMapping(path = "/review/add/{driveId}")
    public ResponseEntity<Review> addReview(@PathVariable("driveId") Long driveId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @Valid @RequestBody ReviewForm reviewForm,
                                       BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {

            Drive drive = driveService.getById(driveId);

            Review review = Review.builder()
                    .rate(Double.parseDouble(reviewForm.getRate()))
                    .reviewText(reviewForm.getText())
                    .author(userService.getByUsername(userDetails.getUsername()))
                    .account(drive.getDriver())
                    .build();

            String errorMessage = reviewValidator.validate(review, drive);

            if (errorMessage == null) {
                reviewService.addReview(review);
                return ResponseEntity.ok(review);
            }

            throw new ReviewNotCreatedException(errorMessage);
        } else {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                    errors.append(error.getDefaultMessage()).append('\n');
            }
            throw new ReviewNotCreatedException(errors.toString());
        }
    }

    @ApiOperation(value = "Get all reviews by user id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    @GetMapping("/reviews/{userId}")
    public ResponseEntity<List<Review>> getAllReviewsAboutUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(reviewService.getAllReviewsAboutUser(userId));
    }

    @ApiOperation(value = "Delete review")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success")
    })
    @GetMapping("/review/delete/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable Long id,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Review review = reviewService.getById(id);
        User currentUser = userService.getByUsername(userDetails.getUsername());
        if (!review.getAuthor().getId().equals(currentUser.getId())) {
            if (!currentUser.getRole().equals(User.Role.ADMIN)) {
                throw new ReviewNotByUserException("It's not your review");
            }
        }

        User acc = review.getAccount();
        acc.getReviews().remove(review);
        reviewService.delete(review);
        userService.updateRating(acc);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Update review")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    @PutMapping(path = "/review/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") Long id,
                                               @RequestBody Review review,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Review currentReview = reviewService.getById(id);
        User currentUser = userService.getByUsername(userDetails.getUsername());
        if (!currentReview.getAuthor().getId().equals(currentUser.getId())) {
            if (!currentUser.getRole().equals(User.Role.ADMIN)) {
                throw new ReviewNotByUserException("It's not your review");
            }
        }

        if (!currentReview.getId().equals(review.getId())) {
            throw new ReviewUpdateException("Different review id's");
        }

        reviewService.update(review);
        return ResponseEntity.ok(review);
    }
}
