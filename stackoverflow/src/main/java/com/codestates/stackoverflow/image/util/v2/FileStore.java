package com.codestates.stackoverflow.image.util.v2;

import com.codestates.stackoverflow.image.entity.v2.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileStore {
    @Value("${file.dir}/")
    private String fileDirPath;

    public List<Image> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                images.add(storeFile(multipartFile));
            }
        }

        return images;
    }

    public Image storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        multipartFile.transferTo(new File(createPath(storeFilename)));

        return Image.builder()
                .originFileName(originalFilename)
                .storePath(storeFilename)
                .build();

    }

    public String createPath(String storeFilename) {
        String viaPath = "images/";
        return fileDirPath+viaPath+storeFilename;
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        String storeFilename = uuid + ext;

        return storeFilename;
    }

    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);
        return ext;
    }
}
