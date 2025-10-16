package com.pastiara.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastiara.app.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

}
