package com.example.todo.service;

import com.example.todo.exception.InformationExistException;
import com.example.todo.model.User;
import com.example.todo.model.request.LoginRequest;
import com.example.todo.model.response.LoginResponse;
import com.example.todo.repository.UserRepository;
import com.example.todo.security.JWTUtils;
import com.example.todo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private MyUserDetails myUserDetails;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager,
                       @Lazy MyUserDetails myUserDetails)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager=authenticationManager;
        this.myUserDetails=myUserDetails;
    }

    public User createUser(User userObject){
        System.out.println("Service Calling createUser");
        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())){
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        }
        else {
            throw new InformationExistException("User with emai "+userObject.getEmailAddress()+" already exist");
        }
    }

    public User findUserByEmailAddress(String email){
        return userRepository.findByEmailAddress(email);
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        new UsernamePasswordAuthenticationToken (loginRequest.getEmail() ,loginRequest.getPassword());
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();
            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));


        } catch (Exception e) {
            return ResponseEntity.ok(new LoginResponse("Error: Email "));
        }

    }
}
