package com.app.brs.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*")
@RequestMapping(path = "/appUser")
public interface AppUserRest {

	@PostMapping(path = "/signUp")
	ResponseEntity<?> signUp(@RequestBody Map<String, String> requestMap);

	@PostMapping(path = "/logIn")
	ResponseEntity<?> logIn(@RequestBody Map<String, String> requestMap);

	@PostMapping(path = "/changePassword")
	ResponseEntity<?> changePassword(@RequestBody Map<String, String> requestMap);

}
