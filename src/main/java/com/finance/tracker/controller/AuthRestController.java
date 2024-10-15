package com.finance.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.tracker.dto.UserDTO;
import com.finance.tracker.exception.NotFoundException;
import com.finance.tracker.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    @Autowired
    private AuthService authService;

   @RequestMapping("/login")
   public ResponseEntity<?> login(@RequestBody UserDTO userDTO) throws NotFoundException{
       return ResponseEntity.ok().body(authService.login(userDTO));
    }

}
