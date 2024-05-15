package com.t3t.frontserver.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis 서버 접속 정보를 담는 프로퍼티 클래스
 *
 * @author woody35545(구건모)
 */
@Data
@ConfigurationProperties(prefix = "t3t.redis")
public class RedisProperties {
    private String redisServerIpAddress;
    private String redisServerPassword;
    private String redisServerPort;
    private Integer redisDatabase;
}
