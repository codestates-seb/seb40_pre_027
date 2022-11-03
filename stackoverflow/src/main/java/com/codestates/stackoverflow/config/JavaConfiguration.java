package com.codestates.stackoverflow.config;

import com.codestates.stackoverflow.auth.utils.CustomAuthorityUtils;
import com.codestates.stackoverflow.member.repository.MemberRepository;
import com.codestates.stackoverflow.member.service.MemberService;
import com.codestates.stackoverflow.member.service.impl.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class JavaConfiguration {

    @Bean
    public MemberService MemberServiceImpl(MemberRepository memberRepository,
                                           PasswordEncoder passwordEncoder,
                                           CustomAuthorityUtils customAuthorityUtils) {
        return new MemberServiceImpl(memberRepository, passwordEncoder, customAuthorityUtils);
    }
}
