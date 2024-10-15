package com.finance.tracker.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.finance.tracker.domain.User;
import com.finance.tracker.dto.UserDTO;
import com.finance.tracker.exception.NotFoundException;
import com.finance.tracker.repository.UserRepository;
import com.finance.tracker.utility.Constants;
import com.finance.tracker.utility.CustomPasswordGenerator;

import lombok.extern.slf4j.Slf4j;

@Scope("singleton")
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public void validate(UserDTO userDTO) throws NotFoundException {
        log.info("Validating user: {}", userDTO);
        if (userDTO == null) {
            throw new NotFoundException("UserDTO is null");
        }
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            throw new NotFoundException("Username is null or empty");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new NotFoundException("Password is null or empty");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new NotFoundException("Email is null or empty");
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void create(UserDTO userDTO, String userCreator) throws NotFoundException {
        log.info("Creating user: {}", userDTO);
        validate(userDTO);
        User user = new User();

        Optional<User> optionalUser = userRepository.findByUsername(userDTO.getUsername());
        if (optionalUser.isPresent()) {
            if (optionalUser.get().getStatus().equals(Constants.ENABLED)) {
                throw new NotFoundException("Username already exists");
            } else {
                user = optionalUser.get();
            }
        } else {
            user = new User();
        }

        Optional<User> optionalUserEmail = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUserEmail.isPresent()) {
            if (optionalUserEmail.get().getStatus().equals(Constants.ENABLED)) {
                throw new NotFoundException("Email already exists");
            } else {
                user = optionalUserEmail.get();
            }
        } else {
            user = new User();
        }

        Optional<String> passwordEncripted = CustomPasswordGenerator.hashPassword(userDTO.getPassword());

        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncripted.get());
        user.setEmail(userDTO.getEmail());
        user.setCreatorUser(userCreator);
        user.setCreationDate(new Date());
        user.setStatus(Constants.ENABLED);

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(UserDTO userDTO, String userModifier) throws NotFoundException {
        try {

            log.info("Updating user: {}", userDTO);
            validate(userDTO);
            User user;

            if (userDTO.getUserId() == null || userDTO.getUserId() <= 0) {
                throw new NotFoundException("Id is null or invalid");

            }

            Optional<User> optionalId = userRepository.findById(userDTO.getUserId());
            user = optionalId.orElseThrow(() -> new NotFoundException("User not found" + userDTO.getUserId()));

            Optional<User> optionalUser = userRepository.findByUsername(userDTO.getUsername());
            if (optionalUser.isPresent() && !optionalUser.get().getUserId().equals(userDTO.getUserId())) {
                if (optionalUser.get().getStatus().equals(Constants.ENABLED)) {
                    throw new NotFoundException("Username already exists");
                }
            }

            Optional<User> optionalUserEmail = userRepository.findByEmail(userDTO.getEmail());
            if (optionalUserEmail.isPresent() && !optionalUserEmail.get().getUserId().equals(userDTO.getUserId())) {
                if (optionalUserEmail.get().getStatus().equals(Constants.ENABLED)) {
                    throw new NotFoundException("Email already exists");
                }
            }

            Optional<String> passwordEncripted = CustomPasswordGenerator.hashPassword(userDTO.getPassword());

            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncripted.get());
            user.setEmail(userDTO.getEmail());
            user.setModifierUser(userModifier);
            user.setModificationDate(new Date());
            user.setStatus(Constants.ENABLED);

            userRepository.save(user);

        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer userId, String userDelete) throws NotFoundException {
        log.info("Deleting user: {}", userId);

        if (userId == null || userId <= 0) {
            throw new NotFoundException("Id is null or invalid");
        }

        Optional<User> optionalId = userRepository.findById(userId);
        User user = optionalId.orElseThrow(() -> new NotFoundException("User not found" + userId));

        user.setModifierUser(userDelete);
        user.setModificationDate(new Date());
        user.setStatus(Constants.DISABLED);

        userRepository.save(user);
    }

}
