package com.example.hython.domain.repose;

import com.example.hython.common.response.BaseResponse;
import com.example.hython.domain.repose.dtos.ReposeRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sum")
@RequiredArgsConstructor
public class ReposeController {

    private final ReposeService reposeService; // ReposeService 주입

//    // 1. 내 Repose 조회
//    @GetMapping("/get-my-repose")
//    public BaseResponse<?> getMyRepose() {
//        var response = reposeService.getMyRepose();
//        return new BaseResponse(response);
//    }

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

//    // 5. 오늘 하루의 결과 기록
//    @PostMapping("/today-repose/{reposeId}")
//    public BaseResponse<?> todayRepose(@RequestBody ReposeRequestDTO.ReposeTodayRequestDTO requestDTO,
//                                       @PathVariable Long reposeId) {
//        reposeService.updateTodayRepose(reposeId, requestDTO);
//        return BaseResponse.success("Today's repose updated successfully");
//    }
//
//    // 6. 캘린더 조회
//    @GetMapping("/calendar")
//    public BaseResponse<?> calendar() {
//        var calendarData = reposeService.getCalendar();
//        return BaseResponse.success(calendarData);
//    }
//
//    // 7. 세부 기록 조회
//    @PostMapping("/detail")
//    public BaseResponse<?> detail(@RequestBody ReposeRequestDTO.ReposeDetailRequestDTO requestDTO) {
//        var detailData = reposeService.getDetail(requestDTO);
//        return BaseResponse.success(detailData);
//    }
}
