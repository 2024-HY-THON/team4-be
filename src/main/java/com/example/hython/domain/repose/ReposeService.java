package com.example.hython.domain.repose;

import com.example.hython.common.exception.BaseException;
import com.example.hython.common.response.BaseResponseStatus;
import com.example.hython.domain.member.Member;
import com.example.hython.domain.member.MemberRepository;
import com.example.hython.domain.member.utils.JWTUtils;
import com.example.hython.domain.repose.dtos.ReposeRequestDTO;
import com.example.hython.domain.repose.dtos.ReposeResponseDTO;
import com.example.hython.domain.repose.dtos.ReposeResponseDTO.ReposeDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReposeService {

    private final ReposeRepository reposeRepository;
    private final MemberRepository memberRepository;
    private final JWTUtils jwtUtils;

    public ReposeResponseDTO.MyReposesDTO getMyRepose() {
        Long id = jwtUtils.getMemberIdByToken(jwtUtils.getToken());
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NOT_FOUND_MEMBER)
        );

        List<Repose> reposes = reposeRepository.findByMemberAndIsDone(member, false);

        return ReposeResponseDTO.MyReposesDTO.builder()
                .reposeCount(reposes.size())
                .reposeDTOs(reposes.stream()
                        .map(repose -> ReposeDTO.builder()
                                .reposeId(repose.getId())
                                .todo(repose.getTodo())
                                .remainingMinutes(repose.getRemainingSeconds() / 60)
                                .remainingSeconds(repose.getRemainingSeconds() % 60)
                                .build())
                        .toList())
                .build();
    }


    @Transactional
    public ReposeResponseDTO.ReposeDTO addRepose(ReposeRequestDTO.ReposeAddRequestDTO reposeRequest) {
        Long id = jwtUtils.getMemberIdByToken(jwtUtils.getToken());
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NOT_FOUND_MEMBER)
        );

        Repose repose = Repose.builder()
                .todo(reposeRequest.getTodo())
                .minutes(reposeRequest.getMinutes())
                .remainingSeconds(reposeRequest.getMinutes() * 60)
                .startDate(LocalDate.now())
                .stopTime(LocalTime.MIN)
                .isDone(false)
                .isPaused(true)
                .member(member)
                .build();

        Repose saved = reposeRepository.save(repose);
        return ReposeResponseDTO.ReposeDTO.builder()
                .reposeId(saved.getId())
                .todo(saved.getTodo())
                .remainingMinutes(repose.getRemainingSeconds() / 60)
                .remainingSeconds(repose.getRemainingSeconds() % 60)
                .build();
    }

    @Transactional
    public ReposeDTO pauseRepose(Long reposeId, ReposeRequestDTO.ReposeStopRequestDTO reposeRequest) {
        Repose repose = reposeRepository.findById(reposeId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_REPOSE));

        if (repose.getIsDone()) {
            throw new BaseException(BaseResponseStatus.ALREADY_DONE_REPOSE);
        }
        if (repose.getIsPaused()) {
            throw new BaseException(BaseResponseStatus.ALREADY_PAUSED_REPOSE);
        }

        repose.pause(LocalTime.now(), reposeRequest.getRemainingSeconds());
        Repose saved = reposeRepository.save(repose);
        return ReposeResponseDTO.ReposeDTO.builder()
                .reposeId(saved.getId())
                .todo(saved.getTodo())
                .remainingMinutes(repose.getRemainingSeconds() / 60)
                .remainingSeconds(repose.getRemainingSeconds() % 60)
                .build();
    }

    @Transactional
    public ReposeDTO resumeRepose(Long reposeId) {
        Repose repose = reposeRepository.findById(reposeId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_REPOSE));

        if (repose.getIsDone()) {
            throw new BaseException(BaseResponseStatus.ALREADY_DONE_REPOSE);
        }

        if (repose.getIsPaused()) {
            repose.resume();
            Repose saved = reposeRepository.save(repose);
            return ReposeResponseDTO.ReposeDTO.builder()
                    .reposeId(saved.getId())
                    .todo(saved.getTodo())
                    .remainingMinutes(repose.getRemainingSeconds() / 60)
                    .remainingSeconds(repose.getRemainingSeconds() % 60)
                    .build();
        }
        throw new BaseException(BaseResponseStatus.NOT_PAUSED_REPOSE);
    }


    public void todayRepose(ReposeRequestDTO.ReposeTodayRequestDTO reposeRequest, Long reposeId) {
        Repose repose = reposeRepository.findById(reposeId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NOT_FOUND_REPOSE)
        );

        // 완료 처리
        // 오늘의 정의 및 감정 등록
        // 종료 시간 설정
        // 저장



        reposeRepository.save(repose);
    }

    public void calendar() {

    }

    public void detail(Repose repose) {

    }
}
