package ru.itis.companionapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.companionapp.models.Bookmark;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findAllByAccountId(Long accountId);

    Optional<Bookmark> findByAccountIdAndDriveId(Long accountId, Long driveId);
}
