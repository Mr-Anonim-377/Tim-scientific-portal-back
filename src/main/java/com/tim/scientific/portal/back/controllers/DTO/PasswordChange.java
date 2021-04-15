package com.tim.scientific.portal.back.controllers.DTO;

import lombok.Data;

@Data
public class PasswordChange {

    private String login;

    private String cod;

    private String newPassword;

    private String confirmPassword;
}
