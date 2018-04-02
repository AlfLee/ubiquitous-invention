package com.telecom.service;

import java.util.List;

import com.telecom.entity.user_entity;

public interface user_service {

	void insert(user_entity user);
	
	List<user_entity> getAll();
	
	user_entity getOne(Integer id);
	
	void update(user_entity user);
	
	void delete(Integer id);
	
	boolean getNamePass(String username,String password);
}
