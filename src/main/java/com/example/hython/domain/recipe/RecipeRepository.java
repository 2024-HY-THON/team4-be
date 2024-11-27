package com.example.hython.domain.recipe;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByMemberId(Long memberId);
    void deleteByRecipe(String recipe);
    boolean existsByRecipe(String recipe);

}
