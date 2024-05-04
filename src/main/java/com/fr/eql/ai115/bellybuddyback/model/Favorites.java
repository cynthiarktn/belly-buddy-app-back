package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "favorites")
@Data
@NoArgsConstructor
public class Favorites {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  private UserEntity user;

  @OneToMany(mappedBy = "favorites", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Recipe> recipes = new HashSet<>();
}
