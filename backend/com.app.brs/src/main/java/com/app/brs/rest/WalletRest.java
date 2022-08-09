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
@RequestMapping(path = "/wallet")
public interface WalletRest {

	@PostMapping(path = "/addMoney")
	ResponseEntity<?> addMoney(@RequestBody Map<String, String> requestMap);

	@GetMapping(path = "/getAmountByUserId/{userId}")
	ResponseEntity<?> getAmountByUserId(@PathVariable String userId);

}
