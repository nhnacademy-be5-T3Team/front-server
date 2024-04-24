package com.t3t.frontserver.config;

import com.t3t.frontserver.auth.error.CustomAuthenticationPoint;
import com.t3t.frontserver.auth.filter.GlobalRefreshFilter;
import com.t3t.frontserver.auth.filter.GlobalTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 시큐리티 설정을 담당하는 config
 * @author joohyun1996(이주현)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * security filter chain 설정
     * 권한별 url 접근 허용, global filter 처리, logout url 설정
     * 쿠키 삭제 및 401,403 예외도 담당한다
     * @param http
     * @throws Exception
     * @author joohyun1996(이주현)
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests((auth) -> auth
                        .antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN")
                        .antMatchers("/myPage/**").authenticated()
                        .antMatchers("/logout").authenticated()
                        .antMatchers("/**").permitAll())
                .addFilterAt(new GlobalTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new GlobalRefreshFilter(), GlobalTokenFilter.class)
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("/");
                })
                .deleteCookies("t3t");
        http
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        if (request.getRequestURI().startsWith("/admin")){
                            // 관리자 권한이 없습니다. 메시지 추가
                            response.sendRedirect("/");
                        }
                        if (request.getRequestURI().startsWith("/myPage")){
                            response.sendRedirect("/login");
                        }
                    }
                })
                .authenticationEntryPoint(new CustomAuthenticationPoint());
        return http.build();
    }
}
