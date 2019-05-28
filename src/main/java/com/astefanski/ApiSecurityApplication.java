package com.astefanski;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.astefanski")
@EntityScan("com.astefanski")
@EnableJpaRepositories("com.astefanski")
@SpringBootApplication
public class ApiSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiSecurityApplication.class, args);
    }

}