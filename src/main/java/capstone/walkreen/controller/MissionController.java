package capstone.walkreen.controller;

import capstone.walkreen.dto.CancelRequest;
import capstone.walkreen.dto.EmailRequest;
import capstone.walkreen.dto.StringResponse;
import capstone.walkreen.dto.SubmitRequest;
import capstone.walkreen.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mission")
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/setmission")
    public ResponseEntity<StringResponse> setMission(@RequestBody EmailRequest emailRequest) {
        return ResponseEntity.ok().body(missionService.setMission(emailRequest));
    }

    @PostMapping("/normal")
    public ResponseEntity<StringResponse> getNormalMission(Pageable pageable, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(missionService.getNormalMission(pageable, httpServletRequest));
    }

    @PostMapping("/daliy")
    public ResponseEntity<StringResponse> getDailyMission(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(missionService.getDailyMission(httpServletRequest));
    }

    @PostMapping("submit")
    public ResponseEntity<StringResponse> submitMission(@RequestBody SubmitRequest submitRequest, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(missionService.submitMission(submitRequest, httpServletRequest));
    }

    @PostMapping("cancel")
    public ResponseEntity<StringResponse> cancelMission(@RequestBody CancelRequest cancelRequest, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(missionService.cancelMission(cancelRequest, httpServletRequest));
    }

    @PostMapping("/test")
    public ResponseEntity<StringResponse> testAPI(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(missionService.testAPI(httpServletRequest));
    }
}
