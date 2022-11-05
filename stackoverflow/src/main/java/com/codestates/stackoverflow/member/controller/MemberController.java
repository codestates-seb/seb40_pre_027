package com.codestates.stackoverflow.member.controller;

import com.codestates.stackoverflow.member.dto.MemberDto;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.mapper.MemberMapper;
import com.codestates.stackoverflow.member.service.impl.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
@Validated
public class MemberController {

    private final MemberServiceImpl memberService;
    private final MemberMapper memberMapper;

    @Autowired
    public MemberController(MemberServiceImpl memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity signup (
            @RequestBody @Valid MemberDto.RequestSignup request) {
        log.info("[회원가입 시작] : {}", request.getEmail());
        Member member = memberMapper.requestSignupToMember(request);

        Member savedMember = memberService.createMember(member);
        log.info(savedMember.toString());

        return new ResponseEntity(
                memberMapper.memberToResponseSignup(savedMember), HttpStatus.CREATED);
    }

    /*
    스택오버플로우 profile 페이지에서 profile과 edit에서 보여주는 정보가 동일하게 진행됨에 따라
    GetMapping의 value를 다중으로 처리하였습니다.
     */
    // 프로필 : edit - profile 정보 불러오기
    @GetMapping(value = {"/profile/edit", "/profile"})
    public ResponseEntity getMemberProfile() {
        Member member = memberService.findMemberProfile();

        return ResponseEntity.ok(memberMapper.memberToProfile(member));
    }

    @PutMapping("/profile/edit")
    public ResponseEntity putMemberProfile(@RequestBody MemberDto.Profile request) {
        Member member = memberMapper.profileToMember(request);
        Member updatedMember = memberService.updateMemberProfile(member);
        return ResponseEntity.ok(memberMapper.memberToProfile(updatedMember));
    }

    @GetMapping("/profile/write")
    public ResponseEntity getMemberActivity() {
        log.info("getMemberActivity 작동");
        Member findMember = memberService.findAuthenticatedMember();

        return ResponseEntity.ok(memberMapper.memberToActivity(findMember));
    }
}
