package com.fr.eql.ai115.bellybuddyback.repository;

import com.fr.eql.ai115.bellybuddyback.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);

}
