package com.example.course_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
			.title("Restful APIs")
			.build();
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO)	//顯示API基本資訊，可不加
				.select()
				//若不想 API 產生相關文件，可在 API 上加上 @ApiIgnore
				.apis(RequestHandlerSelectors.basePackage("com.example.course_project.controller"))
				.paths(PathSelectors.any())
				.build();
	}

}
