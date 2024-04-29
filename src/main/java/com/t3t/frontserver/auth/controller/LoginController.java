package com.t3t.frontserver.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.frontserver.auth.model.request.LoginRequestDto;
import com.t3t.frontserver.auth.service.LoginService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Base64;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    /**
     * 로그인 페이지 뷰 반환
     * @return 로그인 페이지 뷰
     * @author joohyun1996(이주현)
     */
    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "main/page/login";
    }

    /**
     * 로그인 요청 처리
     * @param loginRequestDto,redirectAttributes,resp
     * @return 성공시 : redirect:/, 실패시 : redirect:/login
     * @throws JsonProcessingException
     * @author joohyun1996(이주현)
     */
    @PostMapping("/login")
    public String doLogin(@ModelAttribute @Valid LoginRequestDto loginRequestDto,
                            RedirectAttributes redirectAttributes,
                            HttpServletResponse resp) throws JsonProcessingException {
        try {
            ResponseEntity responseEntity = loginService.login(loginRequestDto);
            String access = responseEntity.getHeaders().getFirst(HttpHeaders.AUTHORIZATION).trim().split(" ")[1];

            Cookie cookie = new Cookie("t3t", access);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(-1);

            resp.addCookie(cookie);
            
            // contextholder에 추가
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(access));

            return "redirect:/";
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "아이디/비밀번호 오류입니다<br>다시 로그인 해주세요");
            return "redirect:/login";
        }
    }

    /**
     * Security Context Holder를 사용하기 위해 임의의 CustomUserDetails를 넣고 반환해주는 메소드
     * @param token
     * @return Authentication
     * @throws JsonProcessingException
     * @author joohyun1996(이주현)
     */
    public UsernamePasswordAuthenticationToken getAuthentication(String token) throws JsonProcessingException {
        String payload = token.split("\\.")[1];
        byte[] decodedPayload = Base64.getDecoder().decode(payload);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(new String(decodedPayload), new TypeReference<Map<String, Object>>() {
        });
        String member = (String) map.get("username");
        String role = (String) map.get("role");

        UserDetails userDetails = User.withUsername(member).password("").roles(role).build();
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
