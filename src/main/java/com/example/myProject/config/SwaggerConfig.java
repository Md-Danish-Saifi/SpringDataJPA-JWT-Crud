package com.example.myProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//	Default method structure
//	@Bean
//	public Docket docket() {
//		return new Docket(DocumentationType.SWAGGER_2).select().build();
//	}

//	@Bean
//	public Docket docket() {
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//				.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"))).build();
//	}

//	@Bean
//	public Docket api() {
//	    return new Docket(DocumentationType.SWAGGER_2)
//	      .select()
//	      .apis(RequestHandlerSelectors.any())
//	      .paths(PathSelectors.any())
//	      .build();
//	}

//	Configuration of swagger ui
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("MyProject Api Documentation")
//				.description("MyProject to define and understand everything quickly.")
//				.version("v1").build();
//	}

    /*-----------------------------------------------------*/

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        ParameterBuilder paramBuilder = new ParameterBuilder();
        paramBuilder.name("Authorization")
	        .modelRef(new ModelRef("string"))
	        .parameterType("header")
	        .required(false)
	        .build();
        List<Parameter> params = new ArrayList<Parameter>();
        params.add(paramBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("All Services")
        		.useDefaultResponseMessages(false)
        		.apiInfo(apiInfo())
        		.select()
        		.apis(RequestHandlerSelectors.basePackage("com.example.myProject.Controller"))
        		.build().globalOperationParameters(params);
    }

    // TODO: Check the proper version of Apache License for closed source and update the right version
    // and URL of license info.
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("API Documentation").description("Note please generate token first using jwt-controller with root username and password").license("Apache License Version 2.0").licenseUrl("").version("2.0").build();
    }
}
