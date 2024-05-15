package com.t3t.frontserver.config;

import com.t3t.frontserver.property.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableRedisRepositories
public class RedisConfig {

    /**
     * RedisConnectionFactory 빈 설정
     *
     * @author woody35545(구건모)
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getRedisServerIpAddress());
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisProperties.getRedisServerPort()));
        redisStandaloneConfiguration.setPassword(redisProperties.getRedisServerPassword());
        redisStandaloneConfiguration.setDatabase(redisProperties.getRedisDatabase());
        System.out.println("redisProperties.getRedisServerIpAddress() = " + redisProperties.getRedisServerIpAddress());
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    /**
     * RedisTemplate 빈 설정
     *
     * @author woody35545(구건모)
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisProperties redisProperties) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory(redisProperties));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}
