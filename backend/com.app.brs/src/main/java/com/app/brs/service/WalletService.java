package com.app.brs.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface WalletService {

	ResponseEntity<?> addMoney(Map<String, String> requestMap);

	ResponseEntity<?> getAmountByUserId(String userId);

}
