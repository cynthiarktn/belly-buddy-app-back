package com.fr.eql.ai115.bellybuddyback.repository;

import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsername(String username);
  Boolean existsByUsername(String username);
  Optional<UserEntity> findByEmail(String email);
}
