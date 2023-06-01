package ru.itis.companionapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.companionapp.models.Warn;
import ru.itis.companionapp.repositories.WarnRepository;
import ru.itis.companionapp.services.WarnService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WarnServiceImpl implements WarnService {
    private final WarnRepository warnRepository;

    @Override
    public List<Warn> getAllByAccountId(Long accountId) {
        return warnRepository.findAllByAccountId(accountId);
    }

    @Override
    public void add(Warn warn) {
        warnRepository.save(warn);
    }
}
