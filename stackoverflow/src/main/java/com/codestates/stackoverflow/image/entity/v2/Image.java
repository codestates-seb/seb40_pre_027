package com.codestates.stackoverflow.image.entity.v2;

import lombok.Builder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String originFileName;
    private String storeFileName;

    @Builder
    public Image(Long id, String originFileName, String storePath) {
        this.imageId = imageId;
        this.originFileName = originFileName;
        this.storeFileName = storePath;
    }
}
