package com.nineswords.oauth.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Create by Jarvis.wang on me's device
 *
 * @author Jarvis.wang  me
 * @date 2018-11-22 15:33
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // Spring会自动寻找同样类型的具体类注入，这里就是JwtUserDetailsServiceImpl了
//    @Resource
//    private UserDetailsService userDetailsService;
//
//    @Resource
//    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder
//                // 设置UserDetailsService
//                .userDetailsService(userDetailsService)
//                // 使用BCrypt进行密码的hash
//                .passwordEncoder(passwordEncoder());
//    }

    /**
     * 装载BCrypt密码编码器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
//        return new JwtAuthenticationTokenFilter();
//    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(false).maxAge(3600);
            }
        };
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/nine/*").permitAll();
    }
}
