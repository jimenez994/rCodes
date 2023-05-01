package com.zeus.rcode.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeus.rcode.models.UserInfo;
import com.zeus.rcode.repositories.UserInfoRepository;

@Service
public class UserInfoServices {
	
	@Autowired
	private UserInfoRepository userInfoRepo;
	
	public UserInfo getUserInfo(Long id) {
		Optional<UserInfo> userInfo = userInfoRepo.findById(id);
		if(userInfo.isPresent()) {
			return userInfo.get();
		}
		return null;
	}
	
	public void addUserInfo(UserInfo userInfo) {
		userInfoRepo.save(userInfo);
	}
	
	public void deleteUserInfo(Long id) {
		userInfoRepo.deleteById(id);
	}

}
