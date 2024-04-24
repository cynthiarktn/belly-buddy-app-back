package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "favorite_list")
@Data
@NoArgsConstructor
public class Favorite {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private UserEntity user;

  private int spoonacularId;
  private String recipeName;
}
