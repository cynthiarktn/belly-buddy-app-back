package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.dto.AuthResponseDto;
import com.fr.eql.ai115.bellybuddyback.dto.LoginDto;
import com.fr.eql.ai115.bellybuddyback.dto.RegisterDto;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import com.fr.eql.ai115.bellybuddyback.security.jwt.JWTGenerator;
import com.fr.eql.ai115.bellybuddyback.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/users/")
public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTGenerator tokenGenerator;
  private final CustomUserDetailsService userDetailsService;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JWTGenerator tokenGenerator,
                        CustomUserDetailsService userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenGenerator = tokenGenerator;
    this.userDetailsService = userDetailsService;
  }

  @PostMapping("register")
  public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
    if (userRepository.existsByUsername(registerDto.getUsername())) {
      logger.error("Error: Username is already taken!");
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }
    UserEntity user = new UserEntity();
    user.setUsername(registerDto.getUsername());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    user.setEmail(registerDto.getEmail());
    userRepository.save(user);
    return ResponseEntity.ok("User registered successfully!");
  }

  @PostMapping("login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginDto.getUsername(),
        loginDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenGenerator.generateToken(authentication);
    return new ResponseEntity<>(new AuthResponseDto(jwt), HttpStatus.OK);
  }
}
