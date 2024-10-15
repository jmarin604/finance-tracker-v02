package com.finance.tracker.service;

import com.finance.tracker.dto.UserDTO;
import com.finance.tracker.exception.NotFoundException;

public interface UserService {

    public void create(UserDTO userDTO, String userCreator) throws NotFoundException;

    public void update(UserDTO userDTO, String userModifier) throws NotFoundException;

    public void delete(Integer userId, String userDelete) throws NotFoundException;

}
