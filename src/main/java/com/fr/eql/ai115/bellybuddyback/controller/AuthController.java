package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.dto.LoginDto;
import com.fr.eql.ai115.bellybuddyback.dto.RegisterDto;
import com.fr.eql.ai115.bellybuddyback.model.Role;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.security.jwt.JwtUtil;
import com.fr.eql.ai115.bellybuddyback.service.CustomUserDetailsService;
import com.fr.eql.ai115.bellybuddyback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/")
public class AuthController {

  private UserService userService;
  private JwtUtil jwtUtil;
  private CustomUserDetailsService userDetailsService;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public AuthController(UserService userService, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("public/users/register")
  public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
    if (userService.existsByUsername(registerDto.getUsername())) {
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }
    UserEntity user = new UserEntity();
    user.setUsername(registerDto.getUsername());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

    Role roles = new Role();
    roles.setName("USER");
    user.setRoles(Collections.singletonList(roles));
    userService.registerNewUser(user);
    return ResponseEntity.ok("User registered successfully!");
  }

  @PostMapping("public/users/login")
  public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtil.generateToken(userDetailsService.loadUserByUsername(loginDto.getUsername()));
    return ResponseEntity.ok(jwt);
  }
}
