package com.codestates.stackoverflow.image.service.v2;

import com.codestates.stackoverflow.image.entity.v2.Image;
import com.codestates.stackoverflow.image.repository.ImageRepository;
import com.codestates.stackoverflow.image.util.v2.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final FileStore fileStore;

    public List<Image> saveImages(List<MultipartFile> multipartFileList) throws IOException {
        List<Image> imageFiles = fileStore.storeFiles(multipartFileList);

        return imageFiles;
    }
}
