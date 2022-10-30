package com.codestates.stackoverflow.config;

import com.codestates.stackoverflow.auth.filter.JwtAuthenticationFilter;
import com.codestates.stackoverflow.auth.filter.JwtVerificationFilter;
import com.codestates.stackoverflow.auth.handler.JwtAccessDeniedHandler;
import com.codestates.stackoverflow.auth.handler.JwtAuthenticationEntryPoint;
import com.codestates.stackoverflow.auth.handler.MemberAuthenticationFailureHandler;
import com.codestates.stackoverflow.auth.handler.MemberAuthenticationSuccessHandler;
import com.codestates.stackoverflow.auth.utils.CustomAuthorityUtils;
import com.codestates.stackoverflow.auth.provider.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Slf4j
public class SecurityConfiguration {


    private final JwtProvider jwtProvider;
    private final CustomAuthorityUtils authorityUtils;

    public SecurityConfiguration(JwtProvider jwtProvider, CustomAuthorityUtils authorityUtils) {
        this.jwtProvider = jwtProvider;
        this.authorityUtils = authorityUtils;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // (1) 추가
                .and()
                .formLogin().disable()
                .httpBasic().disable()

                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())  // (1) 추가
                .accessDeniedHandler(new JwtAccessDeniedHandler())            // (2) 추가
                .and()

                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                                .antMatchers("/users/signup", "/users/login", "/").permitAll()
                                .anyRequest().permitAll());
//                        .anyRequest().hasRole("USER")

        return http.build();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtProvider);
            jwtAuthenticationFilter.setFilterProcessesUrl("/user/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtProvider, authorityUtils);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}
