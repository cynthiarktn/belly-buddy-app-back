package com.fr.eql.ai115.bellybuddyback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipe")
@Data
@NoArgsConstructor
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long spoonacularId;
  private String title;
  private String image;
  private int servings;
  private int readyInMinutes;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "favorites_id")
  @JsonBackReference
  private Favorites favorites;

  @JsonManagedReference
  @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<RecipeIngredients> ingredients = new HashSet<>();

  @ManyToMany
  @JoinTable(
    name = "recipe_dish_type",
    joinColumns = @JoinColumn(name = "recipe_id"),
    inverseJoinColumns = @JoinColumn(name = "dish_type_id"))
  private Set<DishType> dishTypes;


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Recipe other = (Recipe) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Recipe [id=" + id + ", title=" + title + "]";
  }
}
