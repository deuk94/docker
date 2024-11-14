package com.ttallang.docker.user.account.controller;

import com.ttallang.docker.user.account.model.AccountResponse;
import com.ttallang.docker.user.account.service.SignupService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class SignupRestController { // 리턴 타입이 JSON인 컨트롤러.

    private final SignupService signupService;

    public SignupRestController(SignupService signupService) {
        this.signupService = signupService;
    }

    @GetMapping("/oauth2/{SNSType}")
    public ResponseEntity<String> oAuth2Login(@PathVariable("SNSType") String SNSType) {
        String response = signupService.getAuthorizationUrl(SNSType);
        try {
            assert response != null;
        } catch (Exception e) {
            log.error("로그인창에 진입할 수 없습니다. 원인={}", e.getMessage());
            return ResponseEntity.ok("/login/form");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/signup/form/checkExisting/{userName}")
    public ResponseEntity<AccountResponse> checkExistingRolesUserName(@PathVariable String userName) {
        return signupService.isExistingRolesUserName(userName);
    }

    @PostMapping("/signup")
    public ResponseEntity<AccountResponse> signup(@RequestBody Map<String, String> userData) {
        return signupService.signupCustomer(userData);
    }
}
