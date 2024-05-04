package com.fr.eql.ai115.bellybuddyback.repository;

import com.fr.eql.ai115.bellybuddyback.model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
}
