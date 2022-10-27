package com.codestates.stackoverflow.member.service;

import com.codestates.stackoverflow.member.entity.Member;

public interface MemberService {

    Member createMember(Member member);

    //    Member loadMember(Member member);
//
    void deleteMember();
//
//    Member logoutMember(long memberId);


    Member findLoginMember();




}
