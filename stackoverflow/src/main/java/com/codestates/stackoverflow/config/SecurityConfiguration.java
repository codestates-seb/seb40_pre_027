package com.codestates.stackoverflow.config;

import com.codestates.stackoverflow.auth.filter.JwtAuthenticationFilter;
import com.codestates.stackoverflow.auth.filter.JwtVerificationFilter;
import com.codestates.stackoverflow.auth.handler.JwtAccessDeniedHandler;
import com.codestates.stackoverflow.auth.handler.JwtAuthenticationEntryPoint;
import com.codestates.stackoverflow.auth.handler.MemberAuthenticationFailureHandler;
import com.codestates.stackoverflow.auth.handler.MemberAuthenticationSuccessHandler;
import com.codestates.stackoverflow.auth.provider.JwtProvider;
import com.codestates.stackoverflow.auth.utils.CustomAuthorityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableJpaAuditing
@Slf4j
public class SecurityConfiguration {

    private final JwtProvider jwtProvider;
    private final CustomAuthorityUtils authorityUtils;

    @Autowired
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
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // (1) ??????
                .and()
                .formLogin().disable()
                .httpBasic().disable()

                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())  // (1) ??????
                .accessDeniedHandler(new JwtAccessDeniedHandler())            // (2) ??????
                .and()

                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/user/signup", "/user/login", "/").permitAll()
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
                    .addFilter(corsFilter())
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //??? ?????? ?????? ?????? ???????????? ???????????????????????? ????????? ??? ?????? ?????? ??????
        config.addAllowedOriginPattern("*");// ?????? ip ?????? ??????
        config.addExposedHeader("Authorization");
        config.addAllowedHeader("*");// ?????? ????????? ?????? ??????
        config.addAllowedMethod("*");// ?????? http ????????? ?????? ??????
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }
}
