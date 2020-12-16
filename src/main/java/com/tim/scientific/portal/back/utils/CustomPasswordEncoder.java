//package com.tim.scientific.portal.back.utils;
//
//import com.tim.scientific.portal.back.controllers.common.handlers.exception.ApiException;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import java.security.InvalidKeyException;
//import java.security.Key;
//import java.util.Base64;
//
//public class CustomPasswordEncoder implements PasswordEncoder {
//
//    final Cipher cipher;
//    final Key key;
//
//    public CustomPasswordEncoder(Cipher cipher, Key key) {
//        this.cipher = cipher;
//        this.key = key;
//    }
//
//    @Override
//    public String encode(CharSequence rawPassword) {
//        if (rawPassword == null) {
//            throw new IllegalArgumentException("rawPassword cannot be null");
//        }
//        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(12));
//    }
//
//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        if (rawPassword == null) {
//            throw new ApiException(ApiException.createApiError("Password cannot be null", "PasswordIsNull"));
//        }
//        String rawPass = rawPassword.toString();
//
//        if (encodedPassword == null || encodedPassword.length() == 0) {
//            throw new ApiException(ApiException.createApiError("Empty encoded password", "SystemError"));
//        }
//        String decryptedPass;
//        try {
//            cipher.init(Cipher.DECRYPT_MODE, key);
//            byte[] decordedValue = Base64.getDecoder().decode(rawPass);
//            byte[] decValue = cipher.doFinal(decordedValue);
//            decryptedPass = new String(decValue);
//        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
//            throw new ApiException(ApiException.createApiError("Decrypt failed", "SystemError"));
//        }
//
//        return BCrypt.checkpw(decryptedPass, encodedPassword);
//    }
//}
