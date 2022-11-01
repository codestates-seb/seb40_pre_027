package com.codestates.stackoverflow.member.mapper;

import com.codestates.stackoverflow.member.dto.MemberDto;
import com.codestates.stackoverflow.member.entity.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-01T15:23:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member requestSignupToMember(MemberDto.RequestSignup request) {
        if ( request == null ) {
            return null;
        }

        Member member = new Member();

        member.setName( request.getName() );
        member.setEmail( request.getEmail() );
        member.setPassword( request.getPassword() );

        return member;
    }

    @Override
    public MemberDto.ResponseSignup memberToResponseSignup(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.ResponseSignup responseSignup = new MemberDto.ResponseSignup();

        return responseSignup;
    }

    @Override
    public Member profileToMember(MemberDto.Profile request) {
        if ( request == null ) {
            return null;
        }

        Member member = new Member();

        member.setName( request.getName() );
        member.setLocation( request.getLocation() );
        member.setTitle( request.getTitle() );
        member.setIntroduction( request.getIntroduction() );

        return member;
    }

    @Override
    public MemberDto.Profile memberToProfile(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.Profile profile = new MemberDto.Profile();

        return profile;
    }
}
