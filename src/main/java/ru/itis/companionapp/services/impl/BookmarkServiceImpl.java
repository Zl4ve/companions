package ru.itis.companionapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.companionapp.exceptions.BookmarkNotFoundException;
import ru.itis.companionapp.exceptions.DriveNotFoundException;
import ru.itis.companionapp.models.Bookmark;
import ru.itis.companionapp.repositories.BookmarkRepository;
import ru.itis.companionapp.services.BookmarkService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    @Override
    public List<Bookmark> getAllByAccountId(Long accountId) {
        return bookmarkRepository.findAllByAccountId(accountId);
    }

    @Override
    public Bookmark getByAccountIdAndDriveId(Long accountId, Long driveId) {
        return bookmarkRepository.findByAccountIdAndDriveId(accountId, driveId).orElseThrow(() -> new BookmarkNotFoundException("Bookmark not found"));
    }

    @Override
    public void add(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    @Override
    public Bookmark getById(Long id) {
        return bookmarkRepository.findById(id).orElseThrow(() -> new BookmarkNotFoundException("Bookmark not found"));
    }

    @Override
    public void delete(Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
    }
}
