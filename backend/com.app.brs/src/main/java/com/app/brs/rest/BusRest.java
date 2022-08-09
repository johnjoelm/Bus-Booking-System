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
@RequestMapping(path = "/bus")
public interface BusRest {

	@PostMapping(path = "/addBus")
	ResponseEntity<?> addBus(@RequestBody Map<String, String> requestMap);

	@GetMapping(path = "/getAllBus")
	ResponseEntity<?> getAllBus();

	@PostMapping(path = "/getAllBusByDate")
	ResponseEntity<?> getAllBusByDate(@RequestBody Map<String, String> requestMap);

	@PostMapping(path = "/updateBus")
	ResponseEntity<?> updateBus(@RequestBody Map<String, String> requestMap);

}
