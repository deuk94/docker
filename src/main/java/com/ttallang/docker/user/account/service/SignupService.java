package com.ttallang.docker.user.account.service;

import com.ttallang.docker.user.account.model.AccountResponse;
import com.ttallang.docker.user.account.model.CertInfo;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface SignupService {
    // 외부계정 회원가입.
    String getAuthorizationUrl(String SNSType);
    Map<String, Object> processSNSCert(Map<String, String> params);
    // 일반 회원가입.
    ResponseEntity<AccountResponse> isExistingRolesUserName(String userId);
    ResponseEntity<AccountResponse> signupCustomer(Map<String, String> userData);
    String fillOutSignupForm(Map<String, Object> responseBody, String SNSType, CertInfo certInfo, Model model);
}
