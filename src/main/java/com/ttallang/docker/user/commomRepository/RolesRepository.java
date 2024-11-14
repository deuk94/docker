package com.ttallang.docker.user.commomRepository;

import com.ttallang.docker.user.commonModel.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Roles findByUserName(String userName);
}
