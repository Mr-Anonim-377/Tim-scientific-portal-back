package com.tim.scientific.portal.back;

import com.tim.scientific.portal.back.config.BasicAuthenticationEntryPointConfig;
import com.tim.scientific.portal.back.config.CsrfTokenResponseHeaderBindingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EntityScan(basePackages = "com.tim.scientific.portal.back.db.models")
@EnableJpaRepositories()
@SpringBootApplication(scanBasePackages = "com.tim.scientific.portal.back")
@EnableWebMvc
public class BackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new CustomPasswordEncoder(cipher, key);
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BasicAuthenticationEntryPoint basicAuthenticationEntryPointConfig() {
        return new BasicAuthenticationEntryPointConfig();
    }

    @Bean
    public CsrfTokenResponseHeaderBindingFilter csrfTokenResponseHeaderBindingFilter() {
        return new CsrfTokenResponseHeaderBindingFilter();
    }
}
