package com.grngenterprise.springsecurityjwt.controller;

import com.grngenterprise.springsecurityjwt.dto.LoginDTO;
import com.grngenterprise.springsecurityjwt.dto.SessaoDTO;
import com.grngenterprise.springsecurityjwt.model.User;
import com.grngenterprise.springsecurityjwt.repository.UserRepository;
import com.grngenterprise.springsecurityjwt.security.JWTCreator;
import com.grngenterprise.springsecurityjwt.security.JWTObject;
import com.grngenterprise.springsecurityjwt.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public SessaoDTO logar(@RequestBody LoginDTO loginDTO){
        User user = userRepository.findByUsername(loginDTO.getUsername());

        if (user!=null){
            boolean passwordOk = encoder.matches(loginDTO.getPassword(), user.getPassword());
            if (!passwordOk){
                throw new RuntimeException(("Senha Inválida" + loginDTO.getUsername()));
            }
            SessaoDTO sessaoDTO = new SessaoDTO();
            sessaoDTO.setLogin((user.getUsername()));

            JWTObject jwtObject = new JWTObject();
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION));
            jwtObject.setRoles(user.getRoles());
            sessaoDTO.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
            return sessaoDTO;
        } else {
            throw new RuntimeException("Falha ao logar");
        }
    }
}
