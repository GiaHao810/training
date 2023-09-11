package vn.longvan.training.spring.user.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.longvan.training.spring.user.auth.AuthenticationRequest;
import vn.longvan.training.spring.user.auth.AuthenticationResponse;
import vn.longvan.training.spring.user.service.AuthenticationService;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ){
        logger.info("Login");
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/login")
    public String getloginPage(){
        return "login";
    }
}
