package com.example.hython.domain.recipe;

import com.example.hython.domain.member.Member;
import com.example.hython.domain.repose.Repose;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer minutes;

    private Integer satisfaction;

    private String definition;

    private String recipe;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Repose> reposeList;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateInfo(Integer minutes, Integer satisfaction, String definition, String recipe) {
        this.minutes = minutes;
        this.satisfaction = satisfaction;
        this.definition = definition;
        this.recipe = recipe;
    }
}
