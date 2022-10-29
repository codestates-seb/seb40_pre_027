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

@RestController
@RequestMapping("/user")
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
    public ResponseEntity<MemberDto.Response> signup (
            @RequestBody @Valid MemberDto.RegisterPost requestBody) {
        System.out.println("[Member controller] Post 동작");
        Member member = memberMapper.memberRegisterPostDtoToMember(requestBody);

        Member savedMember = memberService.createMember(member);
        log.info(savedMember.toString());

        return new ResponseEntity<MemberDto.Response>(
                memberMapper.memberToMemberResponseDto(savedMember),
                HttpStatus.CREATED);
    }
    // 로그인 된 회원 정보 불러오기
    @GetMapping
    public ResponseEntity getMember() {
        Member member = memberService.findLoginMember();

        return ResponseEntity.ok(memberMapper.memberToMemberResponseDto(member));
    }
    // 회원 정보 수정
    @PutMapping
    public ResponseEntity fetchMember(@RequestBody MemberDto.FetchRequest request) {
        Member member = memberMapper.memberFetchRequestDtoToMember(request);

        Member updatedMember = memberService.updateMember(member);

        MemberDto.FetchResponse response = memberMapper.memberToMemberFetchResponseDto(updatedMember);

        return ResponseEntity.ok(response);
    }

    // 회원 정보 삭제
    @DeleteMapping
    public ResponseEntity deleteMember() {
        // 비밀번호를 전달받아야 하나..?

        memberService.deleteMember();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
