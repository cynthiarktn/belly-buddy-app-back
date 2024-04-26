package com.fr.eql.ai115.bellybuddyback.repository;

import com.fr.eql.ai115.bellybuddyback.model.FavoriteRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipe, Long> {
  List<FavoriteRecipe> findByUserId(Long userId);
  FavoriteRecipe findByIdAndUserId(Long id, Long userId);
}
