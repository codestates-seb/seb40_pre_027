package com.codestates.stackoverflow.member.service.impl;

import com.codestates.stackoverflow.auth.utils.CustomAuthorityUtils;
import com.codestates.stackoverflow.exception.BusinessLogicException;
import com.codestates.stackoverflow.exception.ExceptionCode;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.repository.MemberRepository;
import com.codestates.stackoverflow.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    @Override
    public Member createMember(Member member) {
        verifyExistsNameOrEmail(member.getName(), member.getEmail());

        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member createdMember = memberRepository.save(member);

        return createdMember;
    }

    @Override
    public Member findLoginMember() {

        Member findMember = findAuthenticatedMember();

        return findMember;
    }

    @Override
    public Member updateMember(Member member) {

        Member findMember = findAuthenticatedMember();

        // 프로필 사진 수정 기능 구현 필요

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getLocation())
                .ifPresent(location -> findMember.setLocation(location));
        Optional.ofNullable(member.getTitle())
                .ifPresent(title -> findMember.setTitle(title));
        Optional.ofNullable(member.getIntroduction())
                .ifPresent(introduction -> findMember.setIntroduction(introduction));

        Member updatedMember = memberRepository.save(findMember);

        return updatedMember;
    }

    @Override
    public void deleteMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Member> optionalMember = memberRepository.findByEmail(principal.toString());

        memberRepository.deleteById(optionalMember.orElseThrow(() -> new RuntimeException()).getMemberId());
    }

    // 이름 또는 이메일 중복 유무 체크
    private void verifyExistsNameOrEmail(String name, String email) {
        boolean isExistsName = memberRepository.existsByName(name);
        log.info("[verifyExistsNameOrEmail] - isExistsName : " + isExistsName);
        boolean isExistsEmail = memberRepository.existsByEmail(email);
        log.info("[verifyExistsNameOrEmail] - isExistsEmail : " + isExistsEmail);

        if(isExistsName) {
            throw new BusinessLogicException(ExceptionCode.NAME_EXISTS);
        } 
        if (isExistsEmail) {
            throw new BusinessLogicException(ExceptionCode.EMAIL_EXISTS);
        }
    }

    private Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member findMember = optionalMember
                .orElseThrow(() -> new RuntimeException("존재하는 회원이 아닙니다."));

        return findMember;
    }

    // 멤버 정보 이 메서드 활용할 것!
    private Member findAuthenticatedMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("[getLoginMemberId] " + principal.toString());

        Optional<Member> optionalMember = memberRepository.findByEmail(principal.toString());

        Member findMember = optionalMember.orElseThrow(() -> new RuntimeException("인증된 상태가 아닙니다."));

        return findMember;
    }
}
