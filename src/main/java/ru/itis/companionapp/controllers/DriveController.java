package ru.itis.companionapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.companionapp.dto.forms.CreateDriveForm;
import ru.itis.companionapp.dto.forms.SearchDriveForm;
import ru.itis.companionapp.exceptions.DriveNotFoundException;
import ru.itis.companionapp.models.Drive;
import ru.itis.companionapp.models.User;
import ru.itis.companionapp.security.details.UserDetailsImpl;
import ru.itis.companionapp.services.DriveService;
import ru.itis.companionapp.services.UserService;
import ru.itis.companionapp.utils.validators.CitiesValidator;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/drive")
public class DriveController {

    private final UserService userService;

    private final DriveService driveService;

    private final CitiesValidator citiesValidator;

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String getDriveCreatePage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     ModelMap modelMap) {
        modelMap.put("hasCarInfo", userService.getByUsername(userDetails.getUsername())
                .getDetails()
                .getCarBrand() != null);
        modelMap.put("driveForm", new CreateDriveForm());
        return "html/create_drive";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String createDrive(@Valid @ModelAttribute("driveForm") CreateDriveForm driveForm,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                              ModelMap modelMap) {

        citiesValidator.validate(driveForm.getSource(), driveForm.getDestination(), bindingResult);

        if (!bindingResult.hasErrors()) {
            driveService.createDrive(driveForm, userService.getByUsername(userDetails.getUsername()));
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProfilePage").build();
        } else {
            modelMap.put("hasCarInfo", true);
            return "html/create_drive";
        }
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String getSearchDrivePage(ModelMap modelMap) {
        modelMap.put("searchDriveForm", new SearchDriveForm());
        return "html/find_drive";
    }

    @RequestMapping(path = "/search", method = RequestMethod.POST)
    public String searchDrives(@Valid @ModelAttribute("searchDriveForm") SearchDriveForm searchDriveForm,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                               ModelMap modelMap) {


        citiesValidator.validate(searchDriveForm.getSource(), searchDriveForm.getDestination(), bindingResult);

        if (!bindingResult.hasErrors()) {
            List<Drive> drives = driveService.getByLocationsAndDateWithFreePlaces(searchDriveForm.getSource(),
                    searchDriveForm.getDestination(), searchDriveForm.getDate());
            modelMap.put("drives", drives);
            modelMap.put("currentUser", userService.getByUsername(userDetails.getUsername()));

        }
        return "html/find_drive";
    }

    @RequestMapping(path = "/book/{id}", method = RequestMethod.GET)
    public String bookDrive(@PathVariable Long id,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Drive drive = driveService.getById(id);
        User currentUser = userService.getByUsername(userDetails.getUsername());
        if (drive.getDriver().getId() == currentUser.getId() ||
                drive.getCompanions().size() == drive.getNumberOfPassengers() ||
                drive.getDate().isBefore(LocalDate.now())) {
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("DC#getSearchDrivePage").build();
        }
        driveService.addUserToDrive(currentUser, drive);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getUserDrivesPage").build();
    }

}
