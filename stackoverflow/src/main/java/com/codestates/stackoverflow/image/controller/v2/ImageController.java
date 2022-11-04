package com.codestates.stackoverflow.image.controller.v2;

import com.codestates.stackoverflow.image.util.v2.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final FileStore fileStore;

    @PostMapping("/profile/picture/post")
    public String doPost(@RequestParam("files") List<MultipartFile> files) {

        return "redirect:/user/profile";
    }


    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource processImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.createPath(filename));
    }

    @GetMapping("/images/{filename}")

}
