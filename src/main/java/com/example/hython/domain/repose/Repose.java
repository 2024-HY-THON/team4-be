package com.example.hython.domain.repose;


import com.example.hython.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class Repose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 할 일
    @Column(nullable = false)
    private String todo;

    // 타이머 시작 시간
    private LocalDate startDate;

    // 총 할당된 시간 (분 단위)
    private Integer minutes;

    // 일시 정지 시간
    private LocalTime stopTime;

    // 남은 시간 (초 단위)
    private Integer remainingSeconds; // 추가된 필드

    // 완료 여부
    private Boolean isDone;

    // 타이머가 일시 정지 상태인지 여부
    private Boolean isPaused; // 추가된 필드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void pause(LocalTime stopTime, int remainingSeconds) {
        this.stopTime = stopTime;
        this.remainingSeconds = remainingSeconds;
        this.isPaused = true;
    }

    public void resume() {
        this.isPaused = false;
    }
}

