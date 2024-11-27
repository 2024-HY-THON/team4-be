package com.example.hython.domain.repose;

import com.example.hython.common.response.BaseResponse;
import com.example.hython.domain.repose.dtos.ReposeRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "휴식")
@RestController
@RequestMapping("/sum")
@RequiredArgsConstructor
public class ReposeController {

    private final ReposeService reposeService; // ReposeService 주입

    // 1. 내 Repose 조회
    @Operation(summary = "내 휴식 조회", description = """
            내 휴식 조회 API입니다. 내 휴식을 조회합니다.\n
            현재 완료 되지 않은 휴식만 조회됩니다. 휴식이 완료된 경우는 조회되지 않습니다. 
            """)
    @GetMapping("/get-my-rest")
    public BaseResponse<?> getMyRepose() {
        return new BaseResponse(reposeService.getMyRepose());
    }

    // 2. Repose 추가
    @Operation(summary = "휴식 추가", description = """
            휴식 추가 API입니다. 휴식을 입력받아 휴식을 추가합니다.\n
            해당 휴식의 ID와 남은 시간을 반환합니다. 휴식을 생성 한 순간 바로 시작되는 것이 아닌, 
            휴식 시작 API를 호출하여 서버에게 시작을 알려야 시작할 수 있습니다.
            """)
    @PostMapping("/add-rest")
    public BaseResponse<?> addRepose(@RequestBody ReposeRequestDTO.ReposeAddRequestDTO requestDTO) {
        return new BaseResponse(reposeService.addRepose(requestDTO));
    }

    // 3. 타이머 정지
    @Operation(summary = "휴식 정지", description = """
            휴식 정지 API입니다. 휴식 ID와 정지한 시점에 남은 휴식 시간을 입력받아 휴식을 정지합니다.\n
            해당 API는 휴식을 정지한 시점에 호출하시면 됩니다.\n
            예를 들어 30분짜리 휴식을 10분 후에 정지하면, 20분이 남은 상태로 정지됩니다. 이 경우 20분이 입력되어야 합니다.
            """)
    @PostMapping("/stop-rest/{restId}")
    public BaseResponse<?> stopRepose(@PathVariable Long restId,
                                      @RequestBody ReposeRequestDTO.ReposeStopRequestDTO requestDTO) {
        return new BaseResponse(reposeService.pauseRepose(restId, requestDTO));
    }

    // 4. 타이머 재개
    @Operation(summary = "휴식 시작", description = "휴식 시작 API입니다. 휴식 ID를 입력받아 휴식을 시작합니다."
            + "남은 휴식 시간을 반환합니다.")
    @PostMapping("/start-rest/{restId}")
    public BaseResponse<?> resumeRepose(@PathVariable Long restId) {
        return new BaseResponse(reposeService.resumeRepose(restId));
    }

    // 5. 오늘 하루의 결과 기록
    @Operation(summary = "휴식 완료 및 오늘의 휴식 결과 기록", description = """
            휴식 완료 및 오늘의 휴식 결과 기록 API입니다. 휴식 ID와 오늘의 정의, 오늘의 감정을 입력받아 오늘의 휴식 결과를 기록합니다.\n
            해당 API는 하루가 끝난 후에 호출하시면 됩니다.\n
            현재 빠른 연동을 위해 휴식이 완료되지 않아도 오늘의 결과를 기록할 수 있습니다. 오늘의 결과를 기록하게 되면 해당 휴식은 완료된 것으로 간주됩니다.\n
            이후 휴식 결과를 조회할 때 완료된 휴식만 조회됩니다. 따라서 해당 휴식은 조회되지 않습니다.
            """)
    @PostMapping("/today-rest/{restId}")
    public BaseResponse<?> todayRepose(@RequestBody ReposeRequestDTO.ReposeTodayRequestDTO requestDTO,
                                       @PathVariable Long restId) {
        return new BaseResponse(reposeService.updateTodayRepose(restId, requestDTO));
    }

    // 6. 캘린더 조회
    @Operation(summary = "캘린더 조회", description = """
            캘린더 조회 API입니다. 년도와 월을 입력받아 해당 년도와 월의 완료된 휴식 기록을 조회합니다.\n
            해당 월에 휴식을 한 날짜와 휴식 시간, 감정을 조회합니다. \n
            휴식이 없는 날은 조회되지 않습니다. 또한 휴식을 여러 번 한 경우에는 제일 먼저 한 휴식만 조회됩니다.
            """)
    @GetMapping("/calendar")
    public BaseResponse<?> calendar(@RequestParam Integer year, @RequestParam Integer month) {
        return new BaseResponse(reposeService.getCalendar(year, month));
    }

    // 7. 세부 기록 조회
    @Operation(summary = "세부 기록 조회", description = """
            세부 기록 조회 API입니다. 휴식 ID를 입력받아 해당 휴식의 세부 기록을 조회합니다.\n
            휴식의 총 시간, 감정, 정의, 레시피 만족도를 조회합니다.
            """)
    @PostMapping("/detail/rest/{restId}")
    public BaseResponse<?> detail(@PathVariable Long restId) {
        return new BaseResponse(reposeService.detail(restId));
    }
}
