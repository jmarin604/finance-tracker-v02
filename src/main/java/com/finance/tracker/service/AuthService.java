package com.finance.tracker.service;

import com.finance.tracker.dto.UserDTO;
import com.finance.tracker.exception.NotFoundException;

public interface AuthService {

     public UserDTO login (UserDTO userDTO) throws NotFoundException;

}
