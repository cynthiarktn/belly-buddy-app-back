package com.fr.eql.ai115.bellybuddyback.repository;

import com.fr.eql.ai115.bellybuddyback.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
  Ingredient findByName(String name);
}
