package ru.itis.companionapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.companionapp.dto.forms.UserForm;
import ru.itis.companionapp.services.UserService;
import ru.itis.companionapp.utils.validators.UserExistsValidator;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class SecurityController {

    private final UserService userService;

    private final UserExistsValidator userExistsValidator;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegistrationPage(ModelMap modelMap) {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)) {
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProfilePage").build();
        }
        modelMap.put("user", new UserForm());
        return "html/registration";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String signUp(@Valid @ModelAttribute("user") UserForm userForm,
                         BindingResult bindingResult) {

        userExistsValidator.validate(userForm, bindingResult);

        if (!bindingResult.hasErrors()) {
            userService.signUp(userForm);
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("SC#getLoginPage").build();
        } else {
            return "html/registration";
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)) {
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProfilePage").build();
        }
        return "html/login";
    }

}
