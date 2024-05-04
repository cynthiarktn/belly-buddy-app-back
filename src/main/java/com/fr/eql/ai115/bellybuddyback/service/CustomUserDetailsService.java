package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.dto.auth.RegisterDto;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
  }

  public RegisterDto convertToDto(UserEntity userEntity) {
    RegisterDto userDto = new RegisterDto();
    userDto.setUsername(userEntity.getUsername());
    userDto.setEmail(userEntity.getEmail());
    userDto.setPassword(userEntity.getPassword());
    return userDto;
  }

  public UserEntity getUserProfile(String username) {
    UserEntity user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    return user;
  }


}
