package com.zeus.rcode.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zeus.rcode.models.User;

public interface UserRepository extends CrudRepository<User,Long>{
	User findByEmail(String email);
	User findByUsername(String username);
	List<User> findAll();
	
}