package ru.itis.companionapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.companionapp.models.Drive;

import java.util.List;

@Repository
public interface DriveRepository extends JpaRepository<Drive, Long> {
    @Query(value = "select * from drive d where source = :source and destination = :destination and date = to_timestamp(:date, 'YYYY-MM-DD')\\:\\:timestamp without time zone and (select count(*) from account_drive where drive_id = d.id) != d.number_of_passengers", nativeQuery = true)
    List<Drive> findBySourceAndDestinationAndDateWithFreePlaces(String source, String destination, String date);
}
