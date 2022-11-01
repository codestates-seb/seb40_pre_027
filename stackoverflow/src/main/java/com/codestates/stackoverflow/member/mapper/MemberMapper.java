package com.codestates.stackoverflow.member.mapper;

import com.codestates.stackoverflow.member.dto.MemberDto;
import com.codestates.stackoverflow.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    Member memberLoginPostDtoToMember(MemberDto.LoginPost memberLoginPostDto);

    Member memberRegisterPostDtoToMember(MemberDto.RegisterPost memberRegisterPostDto);

    Member memberFetchRequestDtoToMember(MemberDto.FetchRequest memberFetchDtoRequest);

    MemberDto.FetchResponse memberToMemberFetchResponseDto(Member member);

    MemberDto.Response memberToMemberResponseDto(Member member);


}
