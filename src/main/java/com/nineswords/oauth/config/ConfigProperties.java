package com.nineswords.oauth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Create by Jarvis.wang on me's device
 *
 * @author Jarvis.wang  me
 * @date 2018-11-23 13:34
 */
@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
@ConfigurationProperties(prefix = "refinete")
@Data
public class ConfigProperties {
    private String jwtSecret;
    private Long jwtExpiration;
    private String jwtHeader;
    private String jwtTokenHead;
    private String authServer;
    private String tokenStorePrefix;
    private Map<String, Boolean> swagger;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;
    @Value("${spring.redis.lettuce.shutdown-timeout}")
    private Integer redisTimeout;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private Integer redisMaxActive;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private Integer redisMaxWait;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private Integer redisMaxIdle;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private Integer redisMinIdle;

}
