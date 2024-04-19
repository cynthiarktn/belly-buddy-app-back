package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.dto.LoginDto;
import com.fr.eql.ai115.bellybuddyback.dto.RegisterDto;
import com.fr.eql.ai115.bellybuddyback.model.Role;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.RoleRepository;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import com.fr.eql.ai115.bellybuddyback.security.jwt.JWTGenerator;
import com.fr.eql.ai115.bellybuddyback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/")
public class AuthController {

  private AuthenticationManager authenticationManager;
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;
  private JWTGenerator tokenGenerator;

  @Autowired
  private UserService userService;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager,
                        UserRepository userRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder,
                        JWTGenerator tokenGenerator) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenGenerator = tokenGenerator;
  }

  @PostMapping("public/users/register")
  public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

    // Check if the username is already taken
    if (userRepository.existsByUsername(registerDto.getUsername())) {
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }
    UserEntity user = new UserEntity();
    user.setUsername(registerDto.getUsername());
    user.setEmail(registerDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

    // Fetch the "USER" role from the database, or create it if it doesn't exist
    Role userRole = roleRepository.findByName("USER")
      .orElseGet(() -> {
        Role newUserRole = new Role();
        newUserRole.setName("USER");
        return roleRepository.save(newUserRole);
      });

    // Assign the "USER" role to the new user
    user.setRoles(Collections.singletonList(userRole));

    userService.registerNewUser(user);
    return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
  }



}
