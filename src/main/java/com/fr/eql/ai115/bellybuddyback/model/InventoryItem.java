package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
public class InventoryItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private UserEntity user;

  private String name;
  private Double amount;
  private String unit;
}
