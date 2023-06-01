package ru.itis.companionapp.services;

import ru.itis.companionapp.models.Bookmark;

import java.util.List;

public interface BookmarkService {
    List<Bookmark> getAllByAccountId(Long accountId);

    Bookmark getByAccountIdAndDriveId(Long id, Long id1);

    void add(Bookmark bookmark);

    Bookmark getById(Long id);

    void delete(Bookmark bookmark);
}
