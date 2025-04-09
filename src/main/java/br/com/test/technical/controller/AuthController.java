package br.com.test.technical.controller;

import br.com.test.technical.dto.TokenResponseDTO;
import br.com.test.technical.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/url")
    public ResponseEntity<String> getAuthUrl(){
        return ResponseEntity.ok(authService.buildAuthUrl());
    }

    @GetMapping("/callback")
    public ResponseEntity<TokenResponseDTO> processToken(@RequestParam String code){
        TokenResponseDTO token = authService.getToken(code);
        return ResponseEntity.ok(token);
    }


}
