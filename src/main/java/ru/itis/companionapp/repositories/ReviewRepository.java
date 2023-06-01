package ru.itis.companionapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.companionapp.models.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByAuthorIdAndAccountId(Long authorId, Long accountId);

    List<Review> findAllByAccountId(Long userId);
}
