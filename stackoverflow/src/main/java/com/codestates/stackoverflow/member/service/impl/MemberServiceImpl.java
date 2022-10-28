package com.codestates.stackoverflow.member.service.impl;

import com.codestates.stackoverflow.auth.utils.CustomAuthorityUtils;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.repository.MemberRepository;
import com.codestates.stackoverflow.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        verifyExistsName(member.getName());

        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
        Member createdMember = memberRepository.save(member);

        return createdMember;
    }

    @Override
    public Member findLoginMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("[getLoginMemberId] " + principal.toString());

        Optional<Member> optionalMember = memberRepository.findByEmail(principal.toString());

        Member findMember = optionalMember.orElseThrow(() -> new RuntimeException());

        return findMember;
    }

    @Override
    public Member updateMember(Member member) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("[getLoginMemberId] " + principal.toString());

        Optional<Member> optionalMember = memberRepository.findByEmail(principal.toString());

        Member findMember = optionalMember.orElseThrow(() -> new RuntimeException());

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

    private void verifyExistsEmail(String email) {
        boolean isExistsEmail = memberRepository.existsByEmail(email);

        if(isExistsEmail)
            throw new RuntimeException("이미 존재하는 이메일입니다");
    }

    private void verifyExistsName(String name) {
        boolean isExistsName = memberRepository.existsByName(name);

        if(isExistsName)
            throw new RuntimeException("이미 존재하는 이름입니다");
    }

    private Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member findMember = optionalMember
                .orElseThrow(() -> new RuntimeException("존재하는 회원이 아닙니다."));

        return findMember;
    }

    private boolean verifyPassword(Member member) {
        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());

        Member findMember = optionalMember
                .orElseThrow(() -> new RuntimeException("존재하는 회원이 아닙니다."));

        String rowPassword = member.getPassword();
        String encodedPassword = findMember.getPassword();

//        if (passwordEncoder.matches(rowPassword, encodedPassword)) {
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }
}
