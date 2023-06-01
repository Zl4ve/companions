package ru.itis.companionapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.companionapp.dto.forms.CreateDriveForm;
import ru.itis.companionapp.exceptions.DriveNotFoundException;
import ru.itis.companionapp.models.Drive;
import ru.itis.companionapp.models.User;
import ru.itis.companionapp.repositories.DriveRepository;
import ru.itis.companionapp.services.DriveService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DriveServiceImpl implements DriveService {
    private final DriveRepository driveRepository;

    @Override
    public void createDrive(CreateDriveForm driveForm, User author) {
        Drive drive = Drive.builder()
                .source(driveForm.getSource())
                .destination(driveForm.getDestination())
                .date(LocalDate.parse(driveForm.getDate()))
                .time(LocalTime.parse(driveForm.getTime()))
                .price(Integer.parseInt(driveForm.getPrice()))
                .numberOfPassengers(Integer.parseInt(driveForm.getNumberOfPassengers()))
                .driver(author)
                .build();

        driveRepository.save(drive);
    }

    @Override
    public List<Drive> getByLocationsAndDateWithFreePlaces(String source, String destination, String date) {
        return driveRepository.findBySourceAndDestinationAndDateWithFreePlaces(source, destination, date);
    }

    @Override
    public Drive getById(Long id) {
        return driveRepository.findById(id).orElseThrow(() -> new DriveNotFoundException("Drive not found"));
    }

    @Override
    public void addUserToDrive(User user, Drive drive) {
        drive.addCompanion(user);
        driveRepository.save(drive);
    }
}
