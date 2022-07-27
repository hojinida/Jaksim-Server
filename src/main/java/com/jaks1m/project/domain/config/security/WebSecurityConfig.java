package com.jaks1m.project.domain.config.security;

import com.jaks1m.project.domain.oauth.filter.JwtAuthenticationFilter;
import com.jaks1m.project.domain.oauth.model.JwtTokenProvider;
import com.jaks1m.project.domain.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.jaks1m.project.domain.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.jaks1m.project.domain.oauth.handler.TokenAccessDeniedHandler;
import com.jaks1m.project.domain.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .exceptionHandling()
                    //.authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .accessDeniedHandler(tokenAccessDeniedHandler)
                .and()
                    .authorizeRequests()
                    .antMatchers("/login","/api/v1/user","/auth/**", "/oauth2/**","/login/oauth2/code/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                    .userInfoEndpoint() // oauth2 로그인 성공 후 가져올 때의 설정들
                // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
                    .userService(customOAuth2UserService)
                .and()
                    .failureHandler(oAuth2AuthenticationFailureHandler)
                    .successHandler(oAuth2AuthenticationSuccessHandler);// 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), OAuth2AuthorizationRequestRedirectFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/","/configuration/**","/webjars/**","/swagger*","/swagger*/**","/v2/api-docs");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}


