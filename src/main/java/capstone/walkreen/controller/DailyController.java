package capstone.walkreen.controller;

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
    public ResponseEntity<StringResponse> getNowDaily(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(dailyService.getNowDaily(httpServletRequest));
    }

    @GetMapping("/test")
    public ResponseEntity<StringResponse> testAPI(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(dailyService.setDaily(httpServletRequest));
    }

}
