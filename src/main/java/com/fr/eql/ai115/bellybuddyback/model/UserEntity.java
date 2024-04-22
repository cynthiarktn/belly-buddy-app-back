package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userId;
  private String username;
  private String email;
  private String password;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"))
  private List<Role> roles = new ArrayList<>();
}
