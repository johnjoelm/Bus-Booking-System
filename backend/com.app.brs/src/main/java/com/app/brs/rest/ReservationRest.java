package com.app.brs.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*")
@RequestMapping(path = "/reservation")
public interface ReservationRest {

	@PostMapping(path = "/reserveSeat")
	ResponseEntity<?> reserveSeat(@RequestBody Map<String, Object> requestMap);

	@GetMapping(path = "/getReservationByUserId/{userId}/{status}")
	ResponseEntity<?> getReservationByUserId(@PathVariable String userId, @PathVariable String status);

	@PostMapping(path = "/getBookedSeats")
	ResponseEntity<?> getBookedSeats(@RequestBody Map<String, String> requestMap);

	@GetMapping(path = "/cancleBooking/{reservationId}")
	ResponseEntity<?> cancleBooking(@PathVariable String reservationId);

}
