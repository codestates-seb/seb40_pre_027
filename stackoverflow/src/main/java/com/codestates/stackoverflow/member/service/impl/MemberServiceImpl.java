package com.codestates.stackoverflow.member.service.impl;

import com.codestates.stackoverflow.auth.RefreshToken;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    @Override
    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());

        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        member.setRefreshToken(new RefreshToken());
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member createdMember = memberRepository.save(member);

        return createdMember;
    }

    @Override
    public Member findMemberProfile() {

        Member findMember = findAuthenticatedMember();

        return findMember;
    }

    @Override
    public Member findMemberActivity() {
        Member authMember = findAuthenticatedMember();

        return authMember;
    }

    @Override
    public Member updateMemberProfile(Member member) {

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

    // 이메일 중복 유무 체크
    @Transactional(readOnly = true)
    private void verifyExistsEmail(String email) {

        boolean isExistsEmail = memberRepository.existsByEmail(email);
        log.info("[verifyExistsNameOrEmail] - isExistsEmail : " + isExistsEmail);

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

    // 멤버 정보 이 메서드 활용할 것! (인증된 회원 정보만 이용할 수 있습니다.)
    public Member findAuthenticatedMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("[getLoginMemberId] " + principal.toString());

        Optional<Member> optionalMember = memberRepository.findByEmail(principal.toString());

        Member findMember = optionalMember.orElseThrow(
                () -> new RuntimeException("인증된 상태가 아닙니다."));

        return findMember;
    }


}
