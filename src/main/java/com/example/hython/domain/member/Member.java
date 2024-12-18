package com.example.hython.domain.member;

import com.example.hython.common.exception.BaseException;
import com.example.hython.common.response.BaseResponseStatus;
import com.example.hython.domain.member.dtos.MemberRequestDTO.MemberUpdateRequestDTO;
import com.example.hython.domain.recipe.Recipe;
import com.example.hython.domain.repose.Repose;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
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

    private LocalDate birth;

    private String firebaseToken;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Repose> repose = new ArrayList<>();

    public void addRecipe(Recipe recipe) {
        if (recipes.size() >= 3) {
            throw new BaseException(BaseResponseStatus.ADD_RECIPE_FAIL);
        }
        recipes.add(recipe);
    }

    public void updateInfo(MemberUpdateRequestDTO requestDto) {
        this.birth = requestDto.getBirth();
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.profileImageUrl = requestDto.getProfileImageUrl();
    }

    public void updateFCMToken(String fcmToken) {
        this.firebaseToken = fcmToken;
    }
}
