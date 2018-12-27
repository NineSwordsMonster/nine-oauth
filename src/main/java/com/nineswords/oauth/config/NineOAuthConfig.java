package com.nineswords.oauth.config;

import com.nineswords.oauth.config.cache.RedisIndexConfiguration;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Create by Jarvis.wang on me's device
 *
 * @author Jarvis.wang  me
 * @date 2018-11-22 10:09
 */
@Configuration
@EnableWebMvc
@EnableJpaRepositories(basePackages = "com.nineswords.oauth.dal")
@EnableRedisRepositories(basePackages = "com.nineswords.oauth.dal.redis", indexConfiguration = RedisIndexConfiguration.class)
@Slf4j
public class NineOAuthConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory getQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
