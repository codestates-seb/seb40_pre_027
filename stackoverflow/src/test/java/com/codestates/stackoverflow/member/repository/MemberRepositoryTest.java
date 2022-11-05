package com.codestates.stackoverflow.member.repository;

import com.codestates.stackoverflow.member.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

//    @BeforeEach
//    public void clearRepository() {
//        memberRepository.deleteAll();
//
//    }

    @Test
    @DisplayName("이메일이 존재 유무 메서드 테스트")
    void existsByEmail() {
        Member member = new Member();
        member.setEmail("test@google.com");
        member.setName("test");
        member.setPassword("123456789");

        memberRepository.save(member);

        boolean isExistsMember1 = memberRepository.existsByEmail("test@google.com");
        boolean isExistsMember2 = memberRepository.existsByEmail("NONE@google.com");

        assertTrue(isExistsMember1);
        assertFalse(isExistsMember2);
    }

    @Test
    @DisplayName("이름 존재 유무 메서드 테스트")
    void existsByName() {
        Member member = new Member();
        member.setEmail("test@google.com");
        member.setName("test");
        member.setPassword("123456789");

        memberRepository.save(member);

        boolean isExistsMember1 = memberRepository.existsByName("test");
        boolean isExistsMember2 = memberRepository.existsByName("NONE");

        assertTrue(isExistsMember1);
        assertFalse(isExistsMember2);

    }

}
