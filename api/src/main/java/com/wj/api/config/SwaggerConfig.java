package com.wj.api.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.wj.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()));

    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");  // 用于Swagger UI测试时添加Bearer Token
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("吾家PAD接口文档")
                .description("此接口只为pad端提供")
                //服务条款网址
                //.termsOfServiceUrl("http://www.baidu.com")
                .version("1.0")
                //.contact(new Contact("帅呆了", "url", "email"))
                .build();
    }


}