package com.fr.eql.ai115.bellybuddyback.repository;

import com.fr.eql.ai115.bellybuddyback.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface FavoriteRecipeRepository extends JpaRepository<Favorite, Long> {
  List<Favorite> findByUserId(Long userId);
}
