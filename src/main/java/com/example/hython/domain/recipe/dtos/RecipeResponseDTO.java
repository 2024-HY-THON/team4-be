package com.example.hython.domain.recipe.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RecipeResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeDTO{
        private List<RecipeResponse> recipes;
        private Integer totalRecipeCount;
    }



    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeResponse {
        private Long id;
        private Integer minutes;
        private Integer satisfaction;
        private String definition;
        private String recipe;
    }
}
