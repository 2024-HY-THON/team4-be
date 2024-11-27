package com.example.hython.domain.recipe;

import com.example.hython.common.response.BaseResponse;
import com.example.hython.domain.member.dtos.MemberRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sum")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @Tag(name = "회원 - 레시피")
    @Operation(summary = "내 레시피 조회", description = "내 레시피 조회 API입니다. 내 레시피를 조회합니다.")
    @GetMapping("/get-my-recipe")
    public BaseResponse<?> getMyRecipe () {
        return new BaseResponse<>(recipeService.getMyRecipe());
    }


    // 새로운 레시피 추가하기
    @Tag(name = "회원 - 레시피")
    @Operation(summary = "레시피 추가", description = "레시피 추가 API입니다. 레시피를 입력받아 레시피를 추가합니다.")
    @PostMapping("/add-recipe")
    public BaseResponse<?> addRecipe (@RequestBody MemberRequestDTO.RecipeRequestDTO requestDto) {
        return new BaseResponse<>(recipeService.addRecipe(requestDto));
    }

    // 레시피 삭제하기
    @Tag(name = "회원 - 레시피")
    @Operation(summary = "레시피 삭제", description = "레시피 삭제 API입니다. 레시피를 입력받아 레시피를 삭제합니다.")
    @PostMapping("/delete-recipe")
    public BaseResponse<?> deleteRecipe (@RequestBody MemberRequestDTO.RecipeDeleteRequestDTO requestDto) {
        return new BaseResponse<>(recipeService.deleteRecipe(requestDto));
    }

    // 레시피 수정하기
    @Tag(name = "회원 - 레시피")
    @Operation(summary = "레시피 수정", description = "레시피 수정 API입니다. 레시피를 입력받아 레시피를 수정합니다.")
    @PostMapping("/update-recipe")
    public BaseResponse<?> updateRecipe (@RequestBody MemberRequestDTO.RecipeRequestDTO requestDto) {
        return new BaseResponse<>(recipeService.updateRecipe(requestDto));
    }

}