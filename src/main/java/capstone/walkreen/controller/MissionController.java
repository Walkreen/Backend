package capstone.walkreen.controller;

import capstone.walkreen.dto.*;
import capstone.walkreen.entity.User;
import capstone.walkreen.enumerations.MissionStatus;
import capstone.walkreen.service.AuthService;
import capstone.walkreen.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mission")
public class MissionController {

    private final AuthService authService;
    private final MissionService missionService;

    @GetMapping("/get")
    public ResponseEntity<PageMissionResponse> get(
            @RequestParam("status")
            MissionStatus status, HttpServletRequest httpServletRequest) {

        final User user = authService.getUserByToken(httpServletRequest);
        PageMissionResponse pageMissionResponse;

        if (status.equals(MissionStatus.CAN))
            pageMissionResponse = missionService.getCan(user);
        else if (status.equals(MissionStatus.GOING))
            pageMissionResponse = missionService.getGoing(user);
        else
            pageMissionResponse = missionService.getDone(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pageMissionResponse);
    }

    @PutMapping("join")
    public ResponseEntity<MissionResponse> join(
            @RequestParam("mission") Long missionId,
            HttpServletRequest httpServletRequest) {

        final User user = authService.getUserByToken(httpServletRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(missionService.join(missionId, user));
    }

    @DeleteMapping("cancel")
    public ResponseEntity<MissionResponse> cancel(
            @RequestParam("mission") Long missionId,
            HttpServletRequest httpServletRequest) {

        final User user = authService.getUserByToken(httpServletRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(missionService.cancel(missionId, user));
    }

    @PostMapping("submit")
    public ResponseEntity<MissionResponse> submit(
            @RequestParam("mission") Long missionId,
            HttpServletRequest httpServletRequest) {

        final User user = authService.getUserByToken(httpServletRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(missionService.submit(missionId, user));
    }

    /////////////////////////////// for manager ///////////////////////////////
    @GetMapping("/zget")
    public ResponseEntity<PageMissionResponse> zget() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(missionService.zget());
    }

    @PostMapping("/zset")
    public ResponseEntity<MissionResponse> zset(@RequestBody SetMissionRequest setMissionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(missionService.zset(setMissionRequest));
    }

    @DeleteMapping("/zdelete")
    public ResponseEntity<Boolean> zdelete(@RequestParam Long missionId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(missionService.zdelete(missionId));
    }

    @PutMapping("/zmake")
    public ResponseEntity<Boolean> zmake() {
        return ResponseEntity.ok().body(missionService.zmake());
    }
    /////////////////////////////// for manager ///////////////////////////////
}
