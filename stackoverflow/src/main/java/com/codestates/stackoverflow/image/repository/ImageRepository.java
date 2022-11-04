package com.codestates.stackoverflow.image.repository;

import com.codestates.stackoverflow.image.entity.v1.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {
}
