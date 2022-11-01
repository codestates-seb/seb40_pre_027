package com.codestates.stackoverflow.member.mapper;

import com.codestates.stackoverflow.member.dto.MemberDto;
import com.codestates.stackoverflow.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    Member requestSignupToMember(MemberDto.RequestSignup request);

    MemberDto.ResponseSignup memberToResponseSignup(Member member);

    Member profileToMember(MemberDto.Profile request);

    MemberDto.Profile memberToProfile(Member member);


    MemberDto.Activity memberToActivity(Member member);





}
