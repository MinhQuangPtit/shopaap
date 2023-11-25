package com.example.shopapp.service;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password) throws Exception;
}
