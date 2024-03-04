package com.grngenterprise.springsecurityjwt.service;

import com.grngenterprise.springsecurityjwt.model.User;
import com.grngenterprise.springsecurityjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void createUser (User user){
        String pass = user.getPassword();
        user.setPassword(encoder.encode(pass));
        userRepository.save(user);
    }
}
