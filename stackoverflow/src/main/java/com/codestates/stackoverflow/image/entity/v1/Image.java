package com.codestates.stackoverflow.image.entity.v1;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origFileName;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    @Builder
    public Image(Long id, String origFileName, String fileName, String fileUrl) {
        this.id = id;
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
