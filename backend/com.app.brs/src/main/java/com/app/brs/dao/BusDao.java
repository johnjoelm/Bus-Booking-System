package com.app.brs.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.app.brs.POJO.Bus;

public interface BusDao extends MongoRepository<Bus, String> {

	@Query("{day: ?0,from: ?1,to: ?2}")
	List<Bus> getBusByDay(String day, String from, String to);

}
