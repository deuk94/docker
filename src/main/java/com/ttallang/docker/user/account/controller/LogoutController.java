package com.ttallang.docker.user.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LogoutController {
    @GetMapping("/oauth2/unlink/callback")
    public String unlink() {
        log.info("연결 끊기 성공...");
        return "redirect:/login/form";
    }
}