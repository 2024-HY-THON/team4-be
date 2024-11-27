package com.example.hython.domain.repose;

import com.example.hython.domain.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReposeRepository extends JpaRepository<Repose, Long> {

    List<Repose> findByMemberAndIsDone(Member member, boolean isDone);
}
