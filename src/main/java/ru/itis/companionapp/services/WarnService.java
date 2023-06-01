package ru.itis.companionapp.services;

import ru.itis.companionapp.models.Warn;

import java.util.List;

public interface WarnService {
    List<Warn> getAllByAccountId(Long accountId);

    void add(Warn warn);
}
