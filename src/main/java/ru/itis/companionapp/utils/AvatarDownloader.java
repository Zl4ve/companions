package ru.itis.companionapp.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class AvatarDownloader {

    @Value("${avatar.folder.location}")
    private String avatarFolderLocation;

    public String download(MultipartFile avatar) {
        try (InputStream is = new ByteArrayInputStream(avatar.getBytes())) {
            String avatarLocation = UUID.randomUUID() + "." + FilenameUtils.getExtension(avatar.getOriginalFilename());
            Files.copy(is, Path.of(avatarFolderLocation + avatarLocation));
            return avatarLocation;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
