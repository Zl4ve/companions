package ru.itis.companionapp.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
public class MainController {

    @GetMapping("/")
    public String processRootLink() {
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("MC#getHelloPage").build();
    }

    @GetMapping("/hello")
    public String getHelloPage() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)) {
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProfilePage").build();
        }

        return "html/main_page";
    }
}
