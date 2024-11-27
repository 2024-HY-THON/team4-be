package com.example.hython.domain.repose;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReposeRepository extends JpaRepository<Repose, Long> {
}
