package ru.itis.companionapp.utils.validators;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Component
public class AvatarValidator implements Validator {

    private static final String[] IMAGE_EXTENSIONS = {"img", "png", "jpg", "jpeg"};

    @Override
    public boolean supports(Class<?> clazz) {
        return MultipartFile.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MultipartFile avatar = (MultipartFile) target;

        try {
            if (avatar.getBytes().length > 10 * 1024 * 1024) {
                errors.rejectValue("avatar", "", "File size too large");
            } else {
                String extension = FilenameUtils.getExtension(avatar.getOriginalFilename());
                if (Arrays.stream(IMAGE_EXTENSIONS).filter(e -> e.equals(extension)).findFirst().isEmpty()) {
                    errors.rejectValue("avatar", "", "Wrong file extension");
                }
            }
        } catch (IOException e) {
            errors.rejectValue("avatar", "", "Something went wrong");
        }
    }
}
