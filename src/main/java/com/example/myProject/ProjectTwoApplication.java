package com.example.myProject;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.example.myProject.Model"})  // scan JPA entities
public class ProjectTwoApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public ProjectTwoApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectTwoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ProjectTwoApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("Project runned Successfully: from main Class");
    }
}
