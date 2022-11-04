package com.codestates.stackoverflow.member.service;

import com.codestates.stackoverflow.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    Member createMember(Member member);
    Member findMemberProfile();

    Member updateMemberProfile(Member member);

    Member findMemberActivity();

    void deleteMember();

//    ImageDto updateProfile(MultipartFile file);
}
