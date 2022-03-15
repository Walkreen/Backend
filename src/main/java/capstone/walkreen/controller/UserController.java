package capstone.walkreen.controller;

import capstone.walkreen.dto.LogInRequest;
import capstone.walkreen.dto.SignUpRequest;
import capstone.walkreen.dto.StringResponse;
import capstone.walkreen.dto.UserResponse;
import capstone.walkreen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok().body(userService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> logIn(@RequestBody LogInRequest logInRequest) {
        return ResponseEntity.ok().body(userService.logIn(logInRequest));
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<StringResponse> existByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok().body(userService.existsByEmail(email));

    }
}
