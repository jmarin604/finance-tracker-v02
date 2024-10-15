package com.finance.tracker.service;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.tracker.domain.User;
import com.finance.tracker.dto.UserDTO;
import com.finance.tracker.exception.NotFoundException;
import com.finance.tracker.repository.UserRepository;
import com.finance.tracker.security.CustomSecurityConstants;
import com.finance.tracker.utility.CustomPasswordGenerator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Scope("singleton")
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDTO login(UserDTO userDTO) throws NotFoundException {
        log.info("login()");
        if (userDTO == null) {
            throw new NotFoundException("null userDTO");
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().isBlank()) {
            throw new NotFoundException("El email es requerido.");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            throw new NotFoundException("La password es requerida.");
        }

        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if (!user.isPresent()) {
            throw new NotFoundException("El email o la password son incorrectas.");
        }

        User userEntity = user.get();

        Optional<String> passwordEncripted = CustomPasswordGenerator.hashPassword(userDTO.getPassword());

        // se valida que la contraseña entrante se la misma a la asignada en bd
        if (!passwordEncripted.isPresent() || !userEntity.getPassword().equals(passwordEncripted.get())) {
            throw new NotFoundException("La password es incorrecta.");
        }

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(CustomSecurityConstants.SUPER_SECRET_KEY));
        String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(CustomSecurityConstants.ISSUER_INFO)
                .setSubject(userDTO.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + CustomSecurityConstants.TOKEN_EXPIRATION_TIME))
                .signWith(key).compact();

        userDTO.setUserId(userEntity.getUserId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setPassword(passwordEncripted.get());
        userDTO.setToken(token);
        userDTO.setStatus(userEntity.getStatus());
        return userDTO;
    }

}