package capstone.walkreen.controller;

import capstone.walkreen.dto.*;
import capstone.walkreen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok().body(userService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> logIn(@RequestBody LogInRequest logInRequest) {
        return ResponseEntity.ok().body(userService.logIn(logInRequest));
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<BooleanResponse> existByEmail(@PathVariable("email") @Email String email) {
        return ResponseEntity.ok().body(userService.existsByEmail(email));
    }
}
