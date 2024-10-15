package com.finance.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.tracker.dto.UserDTO;
import com.finance.tracker.exception.NotFoundException;
import com.finance.tracker.service.UserService;
import com.finance.tracker.utility.Utilities;

@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        userService.create(userDTO, user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        userService.update(userDTO, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        userService.delete(userId, user);
        return ResponseEntity.ok().build();
    }

}
