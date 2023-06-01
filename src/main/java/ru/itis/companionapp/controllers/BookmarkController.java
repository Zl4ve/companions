package ru.itis.companionapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.companionapp.exceptions.BookmarkNotFoundException;
import ru.itis.companionapp.exceptions.DriveNotFoundException;
import ru.itis.companionapp.models.Bookmark;
import ru.itis.companionapp.models.Drive;
import ru.itis.companionapp.models.User;
import ru.itis.companionapp.security.details.UserDetailsImpl;
import ru.itis.companionapp.services.BookmarkService;
import ru.itis.companionapp.services.DriveService;
import ru.itis.companionapp.services.UserService;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    private final DriveService driveService;

    private final UserService userService;

    @RequestMapping(path = "/add/{driveId}", method = RequestMethod.GET)
    public String addBookmark(@PathVariable("driveId") Long driveId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Drive drive = driveService.getById(driveId);
        User current = userService.getByUsername(userDetails.getUsername());

        if (drive.getDriver().equals(current) || LocalDate.now().isAfter(drive.getDate())) {
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getBookmarksPage").build();
        }

        try {
            bookmarkService.getByAccountIdAndDriveId(current.getId(), drive.getId());
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getBookmarksPage").build();
        } catch (BookmarkNotFoundException e) {
            Bookmark bookmark = Bookmark.builder()
                    .account(current)
                    .drive(drive)
                    .build();
            bookmarkService.add(bookmark);
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getBookmarksPage").build();
        }
    }

    @RequestMapping(path = "/delete/{bookmarkId}", method = RequestMethod.GET)
    public String deleteBookmark(@PathVariable Long bookmarkId,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Bookmark bookmark = bookmarkService.getById(bookmarkId);
            User currentUser = userService.getByUsername(userDetails.getUsername());
            if (bookmark.getAccount().getId().equals(currentUser.getId())) {
                bookmarkService.delete(bookmark);
            }
        } catch (BookmarkNotFoundException e) {
        }
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getBookmarksPage").build();
    }

}
