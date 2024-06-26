package com.example.uberbackend.controller;

import com.example.uberbackend.dto.*;
import com.example.uberbackend.model.Driver;
import com.example.uberbackend.model.User;
import com.example.uberbackend.model.enums.DrivingStatus;
import com.example.uberbackend.repositories.jpa.DriverRepository;
import com.example.uberbackend.repositories.jpa.RoleRepository;
import com.example.uberbackend.repositories.jpa.UserRepository;
import com.example.uberbackend.security.JwtTokenGenerator;
import com.example.uberbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenGenerator jwtTokenGenerator;
    private UserService userService;
    private DriverRepository driverRepository;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder, JwtTokenGenerator jwtTokenGenerator, UserService userService,
                          DriverRepository driverRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.userService = userService;
        this.driverRepository = driverRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto){
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        }catch(Exception ex){
            return new ResponseEntity<>(new ResponseMessageDto("Credentials are wrong!"), HttpStatus.BAD_REQUEST);
        }

        User loggedUser = (User) authentication.getPrincipal();
        loggedUser.setDrivingStatus(DrivingStatus.ONLINE);
        userService.save(loggedUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenGenerator.generateToken(loggedUser);

        Optional<Driver> driver = this.driverRepository.findByEmail(loginDto.getEmail());

        driver.ifPresent(value -> value.setLastTimeOfLogin(LocalDateTime.now()));

        driver.ifPresent(value -> this.driverRepository.save(value));

        driver.ifPresent(value -> {
            simpMessagingTemplate.convertAndSend("/map-updates/driver-active", new MapDriverDto(value));
        });

        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping("social-login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody String email){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, "social-password"));

        User loggedUser = (User) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenGenerator.generateToken(loggedUser);

        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    // register endpoint for users, for drivers it may be different
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if(userRepository.existsByEmail(registerDto.getEmail())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        User u = new User();
        u.setEmail(registerDto.getEmail());
        u.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        u.setRole(roleRepository.findByName("USER").get());
        userRepository.save(u);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
}
