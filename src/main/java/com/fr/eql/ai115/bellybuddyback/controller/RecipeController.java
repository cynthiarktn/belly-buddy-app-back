package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.dto.RecipeDto;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.service.SpoonacularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/recipe")
public class RecipeController {

  @Autowired
  private SpoonacularService spoonacularService;

  @GetMapping("/getRecipesBasedOnInventory")
  public List<RecipeDto> getRecipesBasedOnInventory(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
    UserEntity user = (UserEntity) userDetails;
    return spoonacularService.getRecipesBasedOnInventory(user.getId());
  }

}
