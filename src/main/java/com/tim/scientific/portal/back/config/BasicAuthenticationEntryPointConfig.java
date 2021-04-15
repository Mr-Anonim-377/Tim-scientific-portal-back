package com.tim.scientific.portal.back.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableBiMap;
import com.tim.scientific.portal.back.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tim.scientific.portal.back.controllers.common.handlers.exception.ApiException.createApiError;

public class BasicAuthenticationEntryPointConfig extends BasicAuthenticationEntryPoint {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authEx) throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        ApiError apiError = createApiError("Не верный логин или пароль", "BAD_CREDENTIALS");
        ServletOutputStream out = response.getOutputStream();
        out.write(objectMapper.writeValueAsString(ImmutableBiMap.of("date", apiError.getDate(),
                "errorTitle", apiError.getErrorTitle(),
                "errorType", apiError.getErrorType())).getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("Tim team");
        super.afterPropertiesSet();
    }

}