package com.fr.eql.ai115.bellybuddyback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Ingredient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long spoonacularId;
  private String name;
  private String image;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "inventory_id")
  @JsonBackReference
  private Inventory inventory;
}
