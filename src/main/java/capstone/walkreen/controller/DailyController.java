package capstone.walkreen.controller;

import capstone.walkreen.dto.*;
import capstone.walkreen.service.DailyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

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
    public ResponseEntity<DailyResponse> setDailyComplete(@RequestBody DailyRequest dailyRequest, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(dailyService.setTodayDaily(dailyRequest, httpServletRequest));
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

    @GetMapping("/getDailyinfo")
    public ResponseEntity<DinfoResponse> getDailyInfo(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(dailyService.getDailyInfo(httpServletRequest));
    }

}
