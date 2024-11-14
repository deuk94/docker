package com.ttallang.docker.user.security.config.token;

import java.util.Random;
import lombok.Getter;

@Getter
public class RandomAuthNumber {

    private final String randomAuthNumber;

    public RandomAuthNumber() {
        this.randomAuthNumber = this.generate();
    }

    // 랜덤한 4자리 인증번호 생성.
    private String generate() {
        Random random = new Random();
        StringBuilder numStr = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            numStr.append(random.nextInt(10));
        }
        return numStr.toString();
    }
}
