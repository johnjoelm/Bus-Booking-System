package com.app.brs.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.brs.rest.ReservationRest;
import com.app.brs.service.ReservationService;

@RestController
public class ReservationRestImpl implements ReservationRest {

	@Autowired
	ReservationService reservationService;

	@Override
	public ResponseEntity<?> reserveSeat(Map<String, Object> requestMap) {
		try {
			return reservationService.reserveSeat(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getReservationByUserId(String userId, String status) {
		try {
			return reservationService.getReservationByUserId(userId, status);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getBookedSeats(Map<String, String> requestMap) {
		try {
			return reservationService.getBookedSeats(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> cancleBooking(String reservationId) {
		try {
			return reservationService.cancleBooking(reservationId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
