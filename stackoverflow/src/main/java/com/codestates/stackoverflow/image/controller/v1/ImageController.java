//package com.codestates.stackoverflow.image.controller.v1;
//
//import com.codestates.stackoverflow.image.dto.ImageDto;
//import com.codestates.stackoverflow.image.service.v1.ImageService;
//import com.codestates.stackoverflow.image.util.v1.MD5Generator;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//
////@RestController
//@RequiredArgsConstructor
//public class ImageController {
////    private final ImageService imageService;
//
////    @PostMapping("/post")
////    public String write(@RequestParam("file") MultipartFile file, Long memberId) {
////        try {
////            String origFileName = file.getOriginalFilename();
////            String fileName = new MD5Generator(origFileName).toString();
////            String savePath = System.getProperty("user.dir") + "\\files";
////
////            if (!new File(savePath).exists()) {
////                try {
////                    new File(savePath).mkdir();
////                }
////                catch(Exception e) {
////                    e.getStackTrace();
////                }
////            }
////            String fileUrl = savePath + "\\" + fileName;
////            file.transferTo(new File(fileUrl));
////
////            ImageDto imageDto = new ImageDto();
////            imageDto.setOrigFileName(origFileName);
////            imageDto.setFileName(fileName);
////            imageDto.setFileUrl(fileUrl);
////
////            Long imageId = imageService.saveImage(imageDto);
//////            memberDto.setImageId(imageId);
//////            memberService.savePost(memberDto);
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
////
//}
