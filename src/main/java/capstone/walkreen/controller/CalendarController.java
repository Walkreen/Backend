package capstone.walkreen.controller;

import capstone.walkreen.dto.DailyResponse;
import capstone.walkreen.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/month")
    public ResponseEntity<DailyResponse> getMonthCalendar(
            @RequestParam("month") LocalDate month,
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(calendarService.getMonthCalendar(month, httpServletRequest));
    }

    @GetMapping("/day")
    public ResponseEntity<DailyResponse> getDayCalendar(
            @RequestParam("month") LocalDate day,
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(calendarService.getDayCalendar(day, httpServletRequest));
    }
}
