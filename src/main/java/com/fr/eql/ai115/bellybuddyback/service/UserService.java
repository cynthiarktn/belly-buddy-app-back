package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.dto.RegisterDto;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }
  public UserEntity registerNewUser(UserEntity userEntity) {
    UserEntity newUserEntity = new UserEntity();
    newUserEntity.setUsername(userEntity.getUsername());
    newUserEntity.setEmail(userEntity.getEmail());
    newUserEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    return userRepository.save(newUserEntity);
  }

  public UserEntity findByUsername(String username) {
    return userRepository.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("Error: User not found."));
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

}
