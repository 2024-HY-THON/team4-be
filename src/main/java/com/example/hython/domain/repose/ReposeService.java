package com.example.hython.domain.repose;

import static java.util.stream.Collectors.groupingBy;

import com.example.hython.common.exception.BaseException;
import com.example.hython.common.response.BaseResponseStatus;
import com.example.hython.domain.member.Member;
import com.example.hython.domain.member.MemberRepository;
import com.example.hython.domain.member.utils.JWTUtils;
import com.example.hython.domain.recipe.Recipe;
import com.example.hython.domain.recipe.RecipeRepository;
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
    private final RecipeRepository recipeRepository;
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
                                .startTime(repose.getStartTime())
                                .todo(repose.getRecipe().getRecipe())
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
        Recipe recipe = recipeRepository.findById(reposeRequest.getRecipeId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NOT_FOUND_RECIPE)
        );

//        // 중복 검사 추가
//        if (reposeRepository.existsByMemberAndRecipe(member, recipe)) {
//            throw new BaseException(BaseResponseStatus.DUPLICATE_REPOSE);
//        }

        Repose repose = Repose.builder()
                .recipe(recipe)
                .minutes(reposeRequest.getMinutes())
                .remainingSeconds(reposeRequest.getMinutes() * 60)
                .startDate(LocalDate.now())
                .startTime(LocalTime.of(reposeRequest.getStartHour(), reposeRequest.getStartMinute()))
                .stopTime(LocalTime.MIN)
                .isDone(false)
                .isAlarm(false)
                .isPaused(true)
                .member(member)
                .build();

        Repose saved = reposeRepository.save(repose);
        return ReposeResponseDTO.ReposeDTO.builder()
                .reposeId(saved.getId())
                .todo(recipe.getRecipe())
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
                .todo(saved.getRecipe().getRecipe())
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
                    .todo(saved.getRecipe().getRecipe())
                    .remainingMinutes(repose.getRemainingSeconds() / 60)
                    .remainingSeconds(repose.getRemainingSeconds() % 60)
                    .build();
        }
        throw new BaseException(BaseResponseStatus.NOT_PAUSED_REPOSE);
    }

    public ReposeResponseDTO.ReposeDetailDTO updateTodayRepose(Long reposeId, ReposeRequestDTO.ReposeTodayRequestDTO reposeRequest) {
        Repose repose = reposeRepository.findById(reposeId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NOT_FOUND_REPOSE)
        );

        repose.updateTodayRepose(reposeRequest);
        Repose saved = reposeRepository.save(repose);

        return ReposeResponseDTO.ReposeDetailDTO.builder()
                .reposeId(saved.getId())
                .todayDefinition(saved.getTodayDefinition())
                .todayEmotion(saved.getTodayEmotion())
                .build();
    }

    public ReposeResponseDTO.ReposeCalendarDTO getCalendar(Integer year, Integer month) {
        Long id = jwtUtils.getMemberIdByToken(jwtUtils.getToken());
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NOT_FOUND_MEMBER)
        );

        // 해당 연/월의 휴식 데이터를 조회
        List<Repose> reposes = reposeRepository.findByMemberAndStartDateBetweenAndIsDone(member,
                LocalDate.of(year, month, 1),
                LocalDate.of(year, month, LocalDate.of(year, month, 1).lengthOfMonth()), true);

        if (reposes.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NOT_EXIST_REPOSE);
        }

        // 같은 날이면 제일 먼저 한걸로
        List<ReposeResponseDTO.ReposeCalendarDetailDTO> reposeCalendarDetailDTOs = reposes.stream()
                .collect(groupingBy(Repose::getStartDate))
                .entrySet().stream()
                .map(entry -> entry.getValue().stream().findFirst().get())
                .map(repose -> ReposeResponseDTO.ReposeCalendarDetailDTO.builder()
                        .reposeId(repose.getId())
                        .reposeTotalMinutes(repose.getMinutes())
                        .todayEmotion(repose.getTodayEmotion())
                        .date(repose.getStartDate())
                        .build())
                .toList();

        return ReposeResponseDTO.ReposeCalendarDTO.builder()
                .year(year)
                .month(month)
                .reposeCalendarDetailDTOs(reposeCalendarDetailDTOs)
                .build();
    }

    public ReposeResponseDTO.ReposeContentDTO detail(Long reposeId) {
        Repose repose = reposeRepository.findById(reposeId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NOT_FOUND_REPOSE)
        );

        if (!repose.getIsDone()){
            throw new BaseException(BaseResponseStatus.NOT_DONE_REPOSE);
        }

        return ReposeResponseDTO.ReposeContentDTO.builder()
                .reposeId(repose.getId())
                .isDone(repose.getIsDone())
                .todayEmotion(repose.getTodayEmotion())
                .todayDefinition(repose.getTodayDefinition())
                .recipeSatisfaction(repose.getSatisfaction())
                .recipeDefinition(repose.getRecipe().getDefinition())
                .reposeTotalMinutes(repose.getMinutes())
                .date(repose.getStartDate())
                .build();

    }
}
