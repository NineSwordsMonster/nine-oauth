package com.nineswords.oauth.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Create by Jarvis.wang on me's device
 *
 * @author Jarvis.wang  me
 * @date 2018-11-22 15:33
 */
@EnableSwagger2
@ConditionalOnProperty(prefix = "nine.swagger", name = "enable", havingValue = "true")
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("rest api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nineswords.oauth.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createWebApi() {
        return new Docket(DocumentationType.SPRING_WEB)
                .groupName("web")
                .apiInfo(webInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nineswords.oauth.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(true)
                .forCodeGeneration(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用 Swagger2构建 RESTful APIs")
                .description("api 文档")
                .termsOfServiceUrl("api")
                .contact(
                        new Contact(
                                "NineSwordsMonster",
                                "https://github.com/NineSwordsMonster",
                                "wangjia_1919@163.com"))
                .version("1.0")
                .build();
    }

    private ApiInfo webInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建 web APIs")
                .description("web 文档")
                .termsOfServiceUrl("web")
                .contact( new Contact(
                        "NineSwordsMonster",
                        "https://github.com/NineSwordsMonster",
                        "wangjia_1919@163.com"))
                .version("1.0")
                .build();
    }
}
