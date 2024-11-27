package com.example.hython.domain.member;

import com.example.hython.common.exception.BaseException;
import com.example.hython.common.response.BaseResponseStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String profileImageUrl;

    private String phoneNumber;

    @ElementCollection
    @CollectionTable(name = "member_recipes", joinColumns = @   JoinColumn(name = "member_id"))
    @Column(name = "recipe")
    private List<String> recipes = new ArrayList<>();

    public void addRecipe(String recipe) {
        if (recipes.contains(recipe)) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_RECIPE);
        }
        if (recipes.size() >= 3) {
            throw new BaseException(BaseResponseStatus.ADD_RECIPE_FAIL);
        }
        recipes.add(recipe);
    }

    // 레시피 삭제
    public void removeRecipe(String recipe) {
        if (!recipes.contains(recipe)) {
            throw new BaseException(BaseResponseStatus.NOT_EXIST_RECIPE);
        }
        recipes.remove(recipe);
    }


    public void updateInfo(String password, String name, String phoneNumber) {
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
