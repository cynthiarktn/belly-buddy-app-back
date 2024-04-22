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
}
