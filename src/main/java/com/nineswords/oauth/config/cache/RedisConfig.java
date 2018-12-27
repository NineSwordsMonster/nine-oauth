package com.nineswords.oauth.config.cache;

import com.nineswords.oauth.config.ConfigProperties;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * Create by Jarvis.wang on me's device
 *
 * @author Jarvis.wang  me
 * @date 2018-11-22 15:33
 */
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
@EnableCaching
public class RedisConfig {
    @Resource
    private ConfigProperties configProperties;

    @Bean(destroyMethod = "shutdown")
    ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean(destroyMethod = "shutdown")
    RedisClient redisClient(ClientResources clientResources) {
        return RedisClient.create(clientResources, RedisURI.create(configProperties.getRedisHost(), configProperties.getRedisPort()));
    }

    @Bean(destroyMethod = "close")
    StatefulRedisConnection<String, String> connection(RedisClient redisClient) {
        return redisClient.connect();
    }

    @Bean
    public LettuceConnectionFactory getLettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(configProperties.getRedisHost());
        redisStandaloneConfiguration.setPort(configProperties.getRedisPort());

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(configProperties.getRedisTimeout()))
                .poolConfig(genericObjectPoolConfig())
                .build();

        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
    }

    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(configProperties.getRedisMaxIdle());
        genericObjectPoolConfig.setMinIdle(configProperties.getRedisMinIdle());
        genericObjectPoolConfig.setMaxTotal(configProperties.getRedisMaxActive());
        genericObjectPoolConfig.setMaxWaitMillis(configProperties.getRedisMaxWait());
        return genericObjectPoolConfig;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    @Bean
    public RedisSerializer<Object> defaultRedisSerializer() {
        return new BeanClassLoaderAwareJdkRedisSerializer();
    }

    @Bean
    public RedisSerializer<Object> gsonRedisSerializer() {
        return new GsonRedisSerializer<>(Object.class);
    }

    @Bean(name = "cacheManager")
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .computePrefixWith(cacheName -> "refinete".concat(":").concat(cacheName).concat(":"))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(gsonRedisSerializer()));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration)
                .build();
    }

    /**
     * RedisTemplate配置
     */
    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplate() {
        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setConnectionFactory(getLettuceConnectionFactory());

        // 设置value的序列化规则和 key的序列化规则
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(gsonRedisSerializer());
//        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public TokenStore tokenStore(LettuceConnectionFactory lettuceConnectionFactory) {
        return new RedisTokenStore(lettuceConnectionFactory);
    }

    static class BeanClassLoaderAwareJdkRedisSerializer implements RedisSerializer<Object>, BeanClassLoaderAware {
        private Converter<Object, byte[]> serializer = new SerializingConverter();
        private Converter<byte[], Object> deserializer;

        @Override
        public void setBeanClassLoader(@Nullable ClassLoader classLoader) {
            this.deserializer = new DeserializingConverter(new DefaultDeserializer(classLoader));
        }

        @Override
        public Object deserialize(byte[] bytes) {
            if (ObjectUtils.isEmpty(bytes)) {
                return null;
            }

            try {
                return deserializer.convert(bytes);
            } catch (Exception ex) {
                throw new SerializationException("Cannot deserialize", ex);
            }
        }

        @Override
        public byte[] serialize(Object object) {
            if (object == null) {
                return new byte[0];
            }
            try {
                return serializer.convert(object);
            } catch (Exception ex) {
                throw new SerializationException("Cannot serialize", ex);
            }
        }

    }
}
