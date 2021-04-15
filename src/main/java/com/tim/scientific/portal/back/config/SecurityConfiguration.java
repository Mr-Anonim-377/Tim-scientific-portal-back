package com.tim.scientific.portal.back.config;

import com.tim.scientific.portal.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    final UserService userService;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final CsrfTokenResponseHeaderBindingFilter csrfTokenResponseHeaderBindingFilter;
    final BasicAuthenticationEntryPoint basicAuthenticationEntryPointConfig;

    public SecurityConfiguration(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder,
                                 CsrfTokenResponseHeaderBindingFilter csrfTokenResponseHeaderBindingFilter,
                                 BasicAuthenticationEntryPoint basicAuthenticationEntryPointConfig) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.csrfTokenResponseHeaderBindingFilter = csrfTokenResponseHeaderBindingFilter;
        this.basicAuthenticationEntryPointConfig = basicAuthenticationEntryPointConfig;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/actuator/**").hasRole("ACTUATOR_ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .and().httpBasic()
                .authenticationEntryPoint(basicAuthenticationEntryPointConfig)
                .and().sessionManagement()
                .and().logout()
                .logoutUrl("/user/logout/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and().cors().disable()
                .csrf().disable();
//                .addFilterAfter(csrfTokenResponseHeaderBindingFilter, CsrfFilter.class)
//                .csrf().csrfTokenRepository(csrfTokenRepository());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/actuator/prometheus").antMatchers("/user/registration");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("_csrf");
        repository.setSessionAttributeName(("_csrf"));
        return repository;
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userService);
    }

}