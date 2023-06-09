package com.scheduler.project.controllers;

import com.scheduler.project.DTO.UserDTO;
import com.scheduler.project.services.userServices.LoginUserService;
import com.scheduler.project.services.userServices.RegisterUserService;
import com.scheduler.project.services.userServices.LoginUserService.LoginUserServiceException;
import com.scheduler.project.services.userServices.RegisterUserService.RegisterUserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final RegisterUserService registerUserService;
    private final LoginUserService loginUserService;

    @Autowired
    public UserController(RegisterUserService registerUserService, LoginUserService loginUserService) {
        this.registerUserService = registerUserService;
        this.loginUserService = loginUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            registerUserService.register(userDTO);
            return ResponseEntity.ok().body("user successfully created");
        }
        catch (RegisterUserServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            loginUserService.login(userDTO);
            return ResponseEntity.ok().body("logged in successfully");
        }
        catch (LoginUserServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
