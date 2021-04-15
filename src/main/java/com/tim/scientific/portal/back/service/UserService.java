package com.tim.scientific.portal.back.service;

import com.tim.scientific.portal.back.controllers.common.handlers.exception.ApiException;
import com.tim.scientific.portal.back.controllers.common.handlers.exception.UploadFileSystemError;
import com.tim.scientific.portal.back.db.models.security.Role;
import com.tim.scientific.portal.back.db.models.security.UpdateRequest;
import com.tim.scientific.portal.back.db.models.security.User;
import com.tim.scientific.portal.back.db.repository.RoleRepository;
import com.tim.scientific.portal.back.db.repository.UpdateRequestRepository;
import com.tim.scientific.portal.back.db.repository.UserRepository;
import com.tim.scientific.portal.back.utils.AbstractService;
import com.tim.scientific.portal.back.utils.EmailsSendler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    EmailsSendler emailsSendler;
    @Autowired
    UpdateRequestRepository updateRequestRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username);
    }

    private User getUser(String username) {
        User user = userRepository.findByUsername(username);
        objectAssert(new UsernameNotFoundException("Такой пользоватлеь не найден в системе"), isNotEmpty(), user);
        return user;
    }

    @Deprecated
    private User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        objectAssert(new UsernameNotFoundException("Такой пользоватлеь не найден в системе"), isNotEmpty(), user);
        return user;
    }

    public User saveUser(User user, Set<Role> roles) {
        assertPassword(user.getPassword(), user.getPasswordConfirm());
        User userFromDB = userRepository.findByUsername(user.getUsername());
        objectAssert(new ApiException("User created already exist", "CREATED_USER_NOW"), isNotEmpty(), userFromDB);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID());
        HashSet<Role> rolesWithId = new HashSet<>();
        for (Role role : roles) {
            String roleName = role.getName();
            Role rolesByName = roleRepository.findByName(roleName);
            if (rolesByName == null) {
                rolesByName = roleRepository.save(new Role(UUID.randomUUID(), roleName));
            }
            rolesWithId.add(rolesByName);
        }
        user.setRoles(rolesWithId);
        CheckedErrorSupplier<User> sqlConsumer = () -> userRepository.save(user);
        return applyHibernateQuery(sqlConsumer);
    }

    private void assertPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new ApiException("password not equals confirm password",
                    "PASSWORD_VALIDATION_ERROR");
        }
    }

    public boolean deleteUser(UUID userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

//    public void passwordUpdate(PasswordChange passwordChange) {
//        User user = getUser(passwordChange.getLogin());
//        String cod = passwordChange.getCod();
//
//        CheckedErrorFunction<String, UpdateRequest> sqlFucntion = requestCod ->
//                updateRequestRepository.findByCod(requestCod);
//        applySqlFunctionAndAssert(new ApiException("Запрос на изменение пароля не найден",
//                        "NOT_FOUND_PASSWORD_UPDATE_REQUEST"), sqlFucntion, cod,
//                o -> o != null && ((UpdateRequest) o).getCod().equals(cod));
//
//        assertPassword(passwordChange.getNewPassword(),passwordChange.getConfirmPassword());
//        user.setPassword(bCryptPasswordEncoder.encode(passwordChange.getNewPassword()));
//
//        CheckedErrorSupplier<User> sqlConsumer = () ->  userRepository.save(user);
//        applyHibernateQuery(sqlConsumer);
//    }

    public void passwordUpdate(String login) {
        User user = getUser(login);
        String password = UUID.randomUUID().toString().replace("-", "");
        user.setPassword(bCryptPasswordEncoder.encode(password));
        CheckedErrorSupplier<User> sqlConsumer = () -> userRepository.save(user);
        applyHibernateQuery(sqlConsumer);
        String body;
        try {
            body = Files.readAllLines(Paths.get("/home/sitesrvgrant/apps/back/resourse/updatePassword.html"))
                    .stream().collect(Collectors.joining("\n")).replace("login",login).replace("pass",password);
        } catch (IOException e) {
            throw new UploadFileSystemError();
        }
        try {
            emailsSendler.sendEmail("Смена пароля на портале Агротехнологии будущего", body, user.getEmail());
        } catch (MessagingException e) {
            throw new ApiException("EMAIL SERVICE ERROR", "Email service error, say admin");
        }
    }

    public void createUpdateRequest(String login) {
        User user = getUser(login);
        String body;

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setRequestId(UUID.randomUUID());
        updateRequest.setUser(user);
        updateRequest.setCod(UUID.randomUUID().toString().substring(0, 8));

        try {
            body = String.format(new String(Files.readAllBytes(Paths.get("static.updateCode.html"))), login,
                    updateRequest.getCod());
        } catch (IOException e) {
            throw new UploadFileSystemError();
        }
        try {
            CheckedErrorConsumer<UpdateRequest> sqlFucntion = updateRequestFoDb ->
                    updateRequestRepository.save(updateRequestFoDb);
            applyHibernateQuery(updateRequest, sqlFucntion);
            emailsSendler.sendEmail("Update Request for" + "", body, user.getEmail());
        } catch (MessagingException e) {
            throw new ApiException("EMAIL SERVICE ERROR", "Email service error, say admin");
        }
    }
}