package com.codestates.stackoverflow.member.controller;

import com.codestates.stackoverflow.member.dto.MemberDto;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.mapper.MemberMapper;
import com.codestates.stackoverflow.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @Autowired
    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<MemberDto.Response> postRegister (
            @RequestBody @Valid MemberDto.RegisterPost requestBody) {

        Member member = memberMapper.memberRegisterPostDtoToMember(requestBody);

        Member savedMember = memberService.createMember(member);
        log.info(savedMember.toString());

        return new ResponseEntity<MemberDto.Response>(
                memberMapper.memberToMemberResponseDto(savedMember),
                HttpStatus.CREATED);
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<MemberDto.Response> postLogin (@RequestBody @Valid MemberDto.LoginPost requestBody) {
        Member member = memberMapper.memberLoginPostDtoToMember(requestBody);

        Member loadMember = memberService.loadMember(member);


        return ResponseEntity.ok(memberMapper.memberToMemberResponseDto(loadMember));
    }

    // 로그아웃
    @PostMapping("/logout/{member-id}")
    public ResponseEntity postLogout(@PathVariable("member-id") long memberId) {
        MemberDto.Response response =
                memberMapper.memberToMemberResponseDto(memberService.logoutMember(memberId));
        return new ResponseEntity(response, HttpStatus.OK);
    }


    @DeleteMapping("/my-page/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
