package com.example.shopapp.service;

import com.example.shopapp.components.JwtTokenUtil;
import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.models.Role;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.Message;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        //Kiểm tra sdt đã tồn tại chưa
        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())){
            throw  new DataIntegrityViolationException("Phone number is already exist");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow();
        if(role.getName().toUpperCase().equals(Role.ADMIN)){
            throw new Exception("You cannot register admin account");
        }
        User user = new User().builder()
                .phoneNumber(userDTO.getPhoneNumber())
                .fullName(userDTO.getFullName())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();

        user.setRole(role);

        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new NoSuchElementException("Invalid phoneNumber/password");
        }
        User user = optionalUser.get();
//        Xác thực với spring security
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("wrong phone number or password");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password, user.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(user);
    }

}
