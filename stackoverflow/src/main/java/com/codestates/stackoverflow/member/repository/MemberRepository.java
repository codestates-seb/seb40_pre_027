package com.codestates.stackoverflow.member.repository;

import com.codestates.stackoverflow.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Transactional(readOnly = true)
    boolean existsByEmail(String email);

    @Transactional(readOnly = true)
    boolean existsByName(String name);

    @Transactional(readOnly = true)
    Optional<Member> findByEmail(String email);

}