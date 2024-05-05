package com.t3t.frontserver.auth.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtils {

    private static final String ANONYMOUS_USER = "anonymousUser";

    /**
     * 유틸리티 클래스이므로 인스턴스화를 방지한다.
     *
     * @author woody35545(구건모)
     */
    private SecurityContextUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 현재 로그인한 사용자의 식별자를 조회한다.
     *
     * @return 현재 로그인한 사용자의 식별자, 로그인하지 않은 경우 null
     * @author woody35545(구건모)
     */
    public static Long getMemberId() {
        if (!isLoggedIn()) {
            return null;
        }
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * 현재 사용자가 로그인 상태인지 확인한다.
     *
     * @return 로그인 상태 여부
     * @author woody35545(구건모)
     */
    public static boolean isLoggedIn() {
        return SecurityContextHolder.getContext().getAuthentication() == null ||
                !ANONYMOUS_USER.equals(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}