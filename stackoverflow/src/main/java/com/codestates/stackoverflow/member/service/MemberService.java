package com.codestates.stackoverflow.member.service;

import com.codestates.stackoverflow.member.entity.Member;

public interface MemberService {

    Member createMember(Member member);

    void deleteMember();

    Member updateMember(Member member);

    Member findLoginMember();




}
