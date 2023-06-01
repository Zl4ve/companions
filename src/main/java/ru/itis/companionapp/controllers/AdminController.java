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
import ru.itis.companionapp.dto.forms.WarnForm;
import ru.itis.companionapp.exceptions.UserNotFoundException;
import ru.itis.companionapp.models.User;
import ru.itis.companionapp.models.Warn;
import ru.itis.companionapp.security.details.UserDetailsImpl;
import ru.itis.companionapp.services.UserService;
import ru.itis.companionapp.services.WarnService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminController {
    private final WarnService warnService;

    private final UserService userService;

    @RequestMapping(path = "/warn/{userId}", method = RequestMethod.GET)
    public String getWarnUserPage(@PathVariable("userId") Long userId,
                                  ModelMap modelMap) {
        userService.getById(userId);
        modelMap.put("warnForm", new WarnForm());
        modelMap.put("userId", userId);
        return "html/warn";
    }

    @RequestMapping(path = "/warn/{userId}", method = RequestMethod.POST)
    public String warnUser(@PathVariable("userId") Long userId,
                           @Valid @ModelAttribute("warnForm") WarnForm warnForm,
                           BindingResult bindingResult,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (bindingResult.hasErrors()) {
            return "html/warn";
        } else {
            if (userService.getByUsername(userDetails.getUsername()).getId() == userId) {
                bindingResult.rejectValue("text", "", "You can't warn yourself");
                return "html/warn";
            } else if (userService.getById(userId).getRole().equals(User.Role.ADMIN)) {
                bindingResult.rejectValue("text", "", "You can't warn admin");
                return "html/warn";
            } else {
                List<Warn> userWarns = warnService.getAllByAccountId(userId);
                if (userWarns.size() == 2) {
                    bindingResult.rejectValue("text", "", "User is already banned");
                    return "html/warn";
                }
                Warn warn = Warn.builder()
                        .account(userService.getById(userId))
                        .warnMessage(warnForm.getText())
                        .build();

                if (userWarns.size() == 1) {
                    userService.ban(userService.getById(userId));
                }

                warnService.add(warn);
                return "redirect:/profile/" + userId;
            }
        }
    }

}
