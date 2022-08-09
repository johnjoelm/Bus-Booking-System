package com.app.brs.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface ReservationService {

	ResponseEntity<?> reserveSeat(Map<String, Object> requestMap);

	ResponseEntity<?> getReservationByUserId(String userId, String status);

	ResponseEntity<?> getBookedSeats(Map<String, String> requestMap);

	ResponseEntity<?> cancleBooking(String reservationId);

}
