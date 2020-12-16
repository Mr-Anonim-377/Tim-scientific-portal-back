//package com.tim.scientific.portal.back.service;
//
//
//import com.tim.scientific.portal.back.config.ConfigProperties;
//import com.tim.scientific.portal.back.db.repository.UsersRepository;
//import com.tim.scientific.portal.back.utils.AbstractService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//import java.util.List;
//
//@Component
//public class UserService extends AbstractService implements UserDetailsService {
//    private final UsersRepository usersRepository;
//
//    private final ConfigProperties configProp;
//
//    public UserService(UsersRepository usersRepository,
//                       ConfigProperties configProp) {
//        this.usersRepository = usersRepository;
//        this.configProp = configProp;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        com.tim.scientific.portal.back.db.models.User user = usersRepository.findByLogin(username);
//        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ACTUATOR_ADMIN"));
//
////        if (username.equals(configProp.getConfigValue("spring.security.user.name"))) {
////            user = new com.tim.scientific.portal.back.db.models.User();
////            user.setLogin(username);
////            user.setPasswordHash("$2a$10$yOqdJ7L/eLm597PRTm2jcu5ygfA0obHOsI072UThP3lMFBypVh9Bm");
//////            authorities = Collections.singletonList(
//////                    new SimpleGrantedAuthority(configProp.getConfigValue("spring.security.user.roles")));
////        }
//
//        objectAssert(new UsernameNotFoundException("User not found"), isNotEmpty(), user);
//
//        return new User(user.getLogin(), user.getPasswordHash(), authorities);
//    }
//}
