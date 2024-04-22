package com.t3t.frontserver.auth.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 로그인 요청을 위한 DTO 클래스
 * @author joohyun1996(이주현)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "이름을 채워주세요")
    @Size(min = 1, max = 100)
    private String username;
    @NotBlank(message = "비밀번호를 채워주세요")
    @Size(min = 1, max = 100)
    private String password;
}
