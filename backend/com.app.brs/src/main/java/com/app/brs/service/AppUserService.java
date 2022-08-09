package com.app.brs.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface AppUserService {

	ResponseEntity<?> signUp(Map<String, String> requestMap);

	ResponseEntity<?> logIn(Map<String, String> requestMap);

	ResponseEntity<?> changePassword(Map<String, String> requestMap);

}
