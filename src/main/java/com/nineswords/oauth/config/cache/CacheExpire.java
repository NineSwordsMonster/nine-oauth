package com.nineswords.oauth.config.cache;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Create by Jarvis.wang on Macbook pro
 *
 * @author Jarvis.wang Erasme
 * @date 2018-12-16
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheExpire {
    /**
     * expire time, default 60s
     */
    @AliasFor("expire")
    long value() default 60L;

    /**
     * expire time, default 60s
     */
    @AliasFor("value")
    long expire() default 60L;
}
