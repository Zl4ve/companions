package ru.itis.companionapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.companionapp.dto.forms.AvatarForm;
import ru.itis.companionapp.dto.forms.CarInfoForm;
import ru.itis.companionapp.dto.forms.PreferenceForm;
import ru.itis.companionapp.models.User;
import ru.itis.companionapp.security.details.UserDetailsImpl;
import ru.itis.companionapp.services.BookmarkService;
import ru.itis.companionapp.services.UserService;
import ru.itis.companionapp.services.PreferenceService;
import ru.itis.companionapp.utils.AvatarDownloader;
import ru.itis.companionapp.utils.api.CarModelApiConnector;
import ru.itis.companionapp.utils.api.CarModelApiParser;
import ru.itis.companionapp.utils.validators.AvatarValidator;
import ru.itis.companionapp.utils.validators.CarApiResponseValidator;
import ru.itis.companionapp.utils.validators.CarBrandValidator;
import ru.itis.companionapp.utils.validators.PreferenceValidator;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    private final AvatarValidator avatarValidator;

    private final AvatarDownloader avatarDownloader;

    private final CarBrandValidator carBrandValidator;

    private final CarModelApiConnector carModelApiConnector;

    private final CarModelApiParser carModelApiParser;

    private final CarApiResponseValidator carApiResponseValidator;

    private final PreferenceValidator preferenceValidator;

    private final PreferenceService preferenceService;

    private final BookmarkService bookmarkService;

    @GetMapping
    public String getProfilePage(ModelMap modelMap, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        modelMap.put("currentUser", userService.getByUsername(userDetails.getUsername()));
        return "html/profile";
    }

    @RequestMapping(path = "/edit/avatar", method = RequestMethod.GET)
    public String getProfileEditPageAvatar(ModelMap modelMap) {
        modelMap.put("avatarForm", new AvatarForm());
        return "html/profile_edit_avatar";
    }

    @RequestMapping(path = "/edit/avatar", method = RequestMethod.POST)
    public String updateAvatar(@ModelAttribute("avatarForm") AvatarForm avatarForm,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        avatarValidator.validate(avatarForm.getAvatar(), bindingResult);

        if (bindingResult.hasErrors()) {
            return "html/profile_edit_avatar";
        } else {
            String avatarRef = avatarDownloader.download(avatarForm.getAvatar());
            userService.updateUserAvatar(userDetails.getUsername(), avatarRef);
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProfilePage").build();
        }
    }

    @RequestMapping(value = "/edit/brand", method = RequestMethod.GET)
    public String getProfileEditBrand(ModelMap modelMap) {
        modelMap.put("carInfo", new CarInfoForm());
        return "html/profile_edit_car_brand";
    }

    @RequestMapping(value = "/edit/brand", method = RequestMethod.POST)
    public String updateCarBrandInfo(@ModelAttribute("carInfo") CarInfoForm carInfoForm,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        carBrandValidator.validate(carInfoForm.getBrand(), bindingResult);

        if (bindingResult.hasErrors()) {
            return "html/profile_edit_car_brand";
        } else {
            redirectAttributes.addFlashAttribute("carInfo", carInfoForm);
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProfileEditModel").build();
        }
    }

    @RequestMapping(path = "/edit/model", method = RequestMethod.GET)
    public String getProfileEditModel(@ModelAttribute("carInfo") CarInfoForm carInfoForm,
                                      ModelMap modelMap,
                                      HttpSession session) {

        if (carInfoForm.getBrand() == null) {
            modelMap.put("error", "You need to choose brand before model");
        } else {
            String json = carModelApiConnector.getCarModelsByBrandJson(carInfoForm.getBrand());
            String jsonValidationResult = carApiResponseValidator.validateJson(json);
            if (jsonValidationResult == null) {
                List<String> models = carModelApiParser.parse(json);
                String modelsValidationResult = carApiResponseValidator.validateModelList(models);

                if (modelsValidationResult == null) {
                    modelMap.put("models", models);
                    modelMap.put("carInfo", carInfoForm);
                    session.setAttribute("models", models);
                    session.setAttribute("carBrand", carInfoForm.getBrand());
                } else {
                    modelMap.put("error", modelsValidationResult);
                }
            } else {
                modelMap.put("error", jsonValidationResult);
            }
        }
        return "html/profile_edit_car_model";
    }

    @RequestMapping(path = "/edit/model", method = RequestMethod.POST)
    public String updateCarModelInfo(@ModelAttribute("carInfo") CarInfoForm carInfoForm,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails,
                                     HttpSession session) {
        userService.updateUserCarInfo(userDetails.getUsername(),
                (String) session.getAttribute("carBrand"),
                carInfoForm.getModel());
        session.setAttribute("models", null);
        session.setAttribute("carBrand", null);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProfilePage").build();
    }

    @RequestMapping(path = "edit/preference", method = RequestMethod.GET)
    public String getProfileEditPreferencesPage(ModelMap modelMap) {
        modelMap.put("preferenceForm", new PreferenceForm());
        return "html/profile_edit_page_preferences";
    }

    @RequestMapping(path = "edit/preference", method = RequestMethod.POST)
    public String addPreference(@Valid @ModelAttribute("preferenceForm") PreferenceForm preferenceForm,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        preferenceValidator.validate(preferenceForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "html/profile_edit_page_preferences";
        }

        preferenceService.addPreference(preferenceForm, userService.getByUsername(userDetails.getUsername()));
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProfilePage").build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String getUserProfilePage(@PathVariable Long id,
                                     ModelMap modelMap,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (id == userService.getByUsername(userDetails.getUsername()).getId()) {
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProfilePage").build();
        }
        modelMap.put("currentUser", userService.getById(id));
        modelMap.put("userRole", userService.getByUsername(userDetails.getUsername()).getRole().toString());
        return "html/profile";
    }

    @RequestMapping(path = "/myDrives", method = RequestMethod.GET)
    public String getUserDrivesPage(ModelMap modelMap,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        modelMap.put("currentUser", userService.getByUsername(userDetails.getUsername()));
        modelMap.put("currentDate", LocalDate.now());
        return "html/user_drives";
    }

    @RequestMapping(path = "/bookmarks")
    public String getBookmarksPage(ModelMap modelMap,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User currentUser = userService.getByUsername(userDetails.getUsername());
        modelMap.put("currentUser", currentUser);
        modelMap.put("bookmarks", bookmarkService.getAllByAccountId(currentUser.getId()));
        return "html/bookmarks";
    }

    @RequestMapping(path = "/edit")
    public String getProfileEditPage() {
        return "html/edit_profile";
    }
}
