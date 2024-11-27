package com.example.hython.domain.repose;

import com.example.hython.domain.member.Member;
import com.example.hython.domain.recipe.Recipe;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReposeRepository extends JpaRepository<Repose, Long> {

    boolean existsByMemberAndRecipe(Member member, Recipe recipe);
    List<Repose> findByMemberAndIsDone(Member member, boolean isDone);
    List<Repose> findByMemberAndStartDateBetweenAndIsDone(Member member, LocalDate startDate, LocalDate endDate, boolean isDone);
}
