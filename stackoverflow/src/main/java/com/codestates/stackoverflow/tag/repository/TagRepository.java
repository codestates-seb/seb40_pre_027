package com.codestates.stackoverflow.tag.repository;

import com.codestates.stackoverflow.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String tagName);

    Page<Tag> findByOrderByAskedTotal(Pageable pageable);
    Page<Tag> findByOrderByTagNameAsc(Pageable pageable);
    Page<Tag> findByOrderByTagIdDesc(Pageable pageable);
}
