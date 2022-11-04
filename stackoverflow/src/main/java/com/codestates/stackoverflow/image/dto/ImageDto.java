package com.codestates.stackoverflow.image.dto;

import com.codestates.stackoverflow.image.entity.v1.Image;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origFileName;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    public Image toEntity() {
        Image build = Image.builder()
                .id(id)
                .origFileName(origFileName)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .build();
        return build;
    }

    @Builder
    public ImageDto(Long id, String origFileName, String fileName, String fileUrl) {
        this.id = id;
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
