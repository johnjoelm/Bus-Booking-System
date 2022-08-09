package com.app.brs.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface BusService {

	ResponseEntity<?> addBus(Map<String, String> requestMap);

	ResponseEntity<?> getAllBus();

	ResponseEntity<?> getAllBusByDate(Map<String, String> requestMap);

	ResponseEntity<?> updateBus(Map<String, String> requestMap);

}
