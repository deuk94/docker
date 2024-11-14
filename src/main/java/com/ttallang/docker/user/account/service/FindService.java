package com.ttallang.docker.user.account.service;

import com.ttallang.docker.user.account.model.RolesUser;

public interface FindService {
    boolean findUserNameByCustomerPhone(String customerPhone);
    RolesUser getUserNameByCustomerPhone(String customerPhone);
    int sendSMS(String to);
}
