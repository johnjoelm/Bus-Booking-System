package com.app.brs.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.app.brs.POJO.AppUser;

public interface AppUserDao extends MongoRepository<AppUser, String> {

	@Query("{username: ?0, password: ?1}")
	AppUser logIn(String username, String password);

	@Query("{username: ?0}")
	AppUser getUserByUserName(String username);

}
