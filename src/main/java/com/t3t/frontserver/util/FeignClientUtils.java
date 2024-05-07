package com.t3t.frontserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;

import java.util.Map;

/**
 * FeignClientException 이 발생한 경우 응답 본분에서 메시지를 추출하기 위한 유틸리티 클래스
 * 메시지가 없는 경우 빈 문자열을 반환한다.
 *
 * @author woody35545(구건모)
 * @see FeignException
 */
public class FeignClientUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getMessageFromFeignException(FeignException e) {
        try {
            String contentUTF8 = e.contentUTF8();
            if (contentUTF8 != null && !contentUTF8.isEmpty()) {
                Map<String, String> contentMap = objectMapper.readValue(contentUTF8, Map.class);
                return contentMap.getOrDefault("message", "");
            }
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
