package com.codestates.stackoverflow.member.mapper;

import com.codestates.stackoverflow.member.dto.MemberDto;
import com.codestates.stackoverflow.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-31T18:11:19+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberLoginPostDtoToMember(MemberDto.LoginPost memberLoginPostDto) {
        if ( memberLoginPostDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setEmail( memberLoginPostDto.getEmail() );
        member.setPassword( memberLoginPostDto.getPassword() );

        return member;
    }

    @Override
    public Member memberRegisterPostDtoToMember(MemberDto.RegisterPost memberRegisterPostDto) {
        if ( memberRegisterPostDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setName( memberRegisterPostDto.getName() );
        member.setEmail( memberRegisterPostDto.getEmail() );
        member.setPassword( memberRegisterPostDto.getPassword() );

        return member;
    }

    @Override
    public Member memberFetchRequestDtoToMember(MemberDto.FetchRequest memberFetchDtoRequest) {
        if ( memberFetchDtoRequest == null ) {
            return null;
        }

        Member member = new Member();

        member.setName( memberFetchDtoRequest.getName() );
        member.setLocation( memberFetchDtoRequest.getLocation() );
        member.setTitle( memberFetchDtoRequest.getTitle() );
        member.setIntroduction( memberFetchDtoRequest.getIntroduction() );

        return member;
    }

    @Override
    public MemberDto.FetchResponse memberToMemberFetchResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.FetchResponse fetchResponse = new MemberDto.FetchResponse();

        fetchResponse.setName( member.getName() );
        fetchResponse.setLocation( member.getLocation() );
        fetchResponse.setTitle( member.getTitle() );
        fetchResponse.setIntroduction( member.getIntroduction() );

        return fetchResponse;
    }

    @Override
    public MemberDto.Response memberToMemberResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.Response response = new MemberDto.Response();

        if ( member.getMemberId() != null ) {
            response.setMemberId( member.getMemberId() );
        }
        response.setName( member.getName() );
        response.setEmail( member.getEmail() );
        List<String> list = member.getRoles();
        if ( list != null ) {
            response.setRoles( new ArrayList<String>( list ) );
        }
        response.setCreatedDate( member.getCreatedDate() );
        response.setLoginDate( member.getLoginDate() );

        return response;
    }
}
