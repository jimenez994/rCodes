package com.zeus.rcode.repositories;

import org.springframework.data.repository.CrudRepository;

import com.zeus.rcode.models.UserInfo;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long>{
	
	
}
