//package com.codestates.stackoverflow.image.service.v1;
//
//import com.codestates.stackoverflow.image.dto.ImageDto;
//import com.codestates.stackoverflow.image.entity.v1.Image;
//import com.codestates.stackoverflow.image.repository.ImageRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class ImageService {
//    private final ImageRepository imageRepository;
//
//    @Transactional
//    public Long saveImage(ImageDto imageDto) {
//        return imageRepository.save(imageDto.toEntity()).getId();
//    }
//
//    @Transactional
//    public ImageDto getImage(Long id) {
//        Image image = imageRepository.findById(id).get();
//
//        ImageDto imageDto = ImageDto.builder()
//                .id(id)
//                .origFileName(image.getOrigFileName())
//                .fileName(image.getFileName())
//                .fileUrl(image.getFileUrl())
//                .build();
//        return imageDto;
//    }
//}
