package com.app.brs.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.app.brs.POJO.Wallet;

public interface WalletDao extends MongoRepository<Wallet, String> {

	@Query("{userId: ?0}")
	Wallet getWalletByUserId(String userId);

}
