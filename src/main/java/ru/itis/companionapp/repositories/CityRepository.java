package ru.itis.companionapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.companionapp.models.City;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = "select c from City c where c.name like :startsWith%")
    List<City> findStartingWith(String startsWith);

    Optional<City> findByName(String name);
}
