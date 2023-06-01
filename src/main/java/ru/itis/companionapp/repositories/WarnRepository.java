package ru.itis.companionapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.companionapp.models.Warn;

import java.util.List;

@Repository
public interface WarnRepository extends JpaRepository<Warn, Long> {
    List<Warn> findAllByAccountId(Long accountId);
}
