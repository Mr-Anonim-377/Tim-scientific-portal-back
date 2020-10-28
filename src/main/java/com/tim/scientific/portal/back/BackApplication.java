package com.tim.scientific.portal.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EntityScan(basePackages = "com.tim.scientific.portal.back.db.models")
@EnableJpaRepositories()
@SpringBootApplication(scanBasePackages = "com.tim.scientific.portal.back")
@EnableWebMvc
public class BackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

}
