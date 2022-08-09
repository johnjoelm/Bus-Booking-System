package com.app.brs.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.app.brs.POJO.Reservation;

public interface ReservationDao extends MongoRepository<Reservation, String> {

	@Query("{'appUser._id':?0, 'status':?1}")
	List<Reservation> getReservationByUserId(String userId, String status);

//	@Query("{'appUser._id':?0}")
//	List<Reservation> findByAppUserrrrrr(String userId);

	@Query(value = "{ 'bus._id' : ?0,'journyDate' : ?1,'status' : ?2}", fields = "{ seats : 1 }")
	List<Reservation> getBookedSeatsByIdAndDate(String busId, String date, String Status);

	@Query(value = "{'id':?0}")
	Reservation getReservationByReservationId(String reservationId);

}
