package com.example.hython.domain.recipe;

import com.example.hython.common.exception.BaseException;
import com.example.hython.common.response.BaseResponseStatus;
import com.example.hython.domain.member.Member;
import com.example.hython.domain.member.MemberRepository;
import com.example.hython.domain.member.dtos.MemberRequestDTO;
import com.example.hython.domain.member.utils.JWTUtils;
import com.example.hython.domain.recipe.dtos.RecipeResponseDTO;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final JWTUtils jwtUtils;


    @Transactional
    public Long addRecipe(MemberRequestDTO.RecipeRequestDTO requestDto) {
        String token = jwtUtils.getToken();
        Long memberId = jwtUtils.getMemberIdByToken(token);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_MEMBER));

        if (recipeRepository.existsByRecipe(requestDto.getRecipe())) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_RECIPE);
        }

        Recipe recipe = Recipe.builder()
                .definition(requestDto.getDefinition())
                .minutes(requestDto.getMinutes())
                .satisfaction(requestDto.getSatisfaction())
                .recipe(requestDto.getRecipe())
                .member(member)
                .build();


        member.addRecipe(recipe);

        Recipe savedRecipe = recipeRepository.save(recipe);

        return savedRecipe.getId();
    }

    @Transactional
    public boolean deleteRecipe(Long recipeId) {

        if (!recipeRepository.existsById(recipeId)) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND_RECIPE);
        }

        recipeRepository.deleteById(recipeId);

        return true;
    }

    @Transactional
    public RecipeResponseDTO.RecipeDTO getMyRecipe() {
        String token = jwtUtils.getToken();
        Long memberId = jwtUtils.getMemberIdByToken(token);
        List<Recipe> recipes = recipeRepository.findAllByMemberId(memberId);

        if (recipes.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND_RECIPE);
        }

        return RecipeResponseDTO.RecipeDTO.builder()
                .recipes(recipes.stream()
                        .map(recipe -> RecipeResponseDTO.RecipeResponse.builder()
                                .id(recipe.getId())
                                .definition(recipe.getDefinition())
                                .minutes(recipe.getMinutes())
                                .satisfaction(recipe.getSatisfaction())
                                .recipe(recipe.getRecipe())
                                .build())
                        .toList())
                .totalRecipeCount(recipes.size())
                .build();
    }

    @Transactional
    public Long updateRecipe(MemberRequestDTO.RecipeRequestDTO requestDto, Long recipeId) {
        String token = jwtUtils.getToken();
        Long memberId = jwtUtils.getMemberIdByToken(token);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_MEMBER));

        if (!recipeRepository.existsById(recipeId)) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND_RECIPE);
        }

        Recipe recipe  = recipeRepository.findById(recipeId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NOT_FOUND_RECIPE));

        recipe.updateInfo(requestDto.getMinutes(), requestDto.getSatisfaction(), requestDto.getDefinition(), requestDto.getRecipe());
        return recipeRepository.save(recipe).getId();
    }
}
