package ru.itis.companionapp.services;

import ru.itis.companionapp.dto.forms.CreateDriveForm;
import ru.itis.companionapp.models.Drive;
import ru.itis.companionapp.models.User;

import java.util.List;

public interface DriveService {
    void createDrive(CreateDriveForm driveForm, User author);
    List<Drive> getByLocationsAndDateWithFreePlaces(String source, String destination, String date);
    Drive getById(Long id);
    void addUserToDrive(User user, Drive drive);
}
