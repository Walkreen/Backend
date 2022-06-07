package capstone.walkreen.controller;

import capstone.walkreen.dto.DailyRequest;
import capstone.walkreen.dto.DailyResponse;
import capstone.walkreen.dto.EmailRequest;
import capstone.walkreen.dto.StringResponse;
import capstone.walkreen.service.DailyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily")
public class DailyController {

    private final DailyService dailyService;

    @GetMapping("/today")
    public ResponseEntity<DailyResponse> getTodayDaily(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(dailyService.getTodayDaily(httpServletRequest));
    }

    @PostMapping("/setToday")
    public ResponseEntity<DailyResponse> setTodayDaily(DailyRequest dailyRequest, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(dailyService.getTodayDaily(httpServletRequest));
    }

    @GetMapping("/month")
    public ResponseEntity<DailyResponse> getMonthDaily(
            @RequestParam(value = "year") Integer year, @RequestParam(value = "month") Integer month,
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(dailyService.getMonthDaily(year, month, httpServletRequest));
    }

    @GetMapping("/test")
    public ResponseEntity<StringResponse> testAPI(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(dailyService.setDaily(httpServletRequest));
    }

}
