package com.app.brs.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.brs.rest.WalletRest;
import com.app.brs.service.WalletService;

@RestController
public class WalletRestImpl implements WalletRest {

	@Autowired
	WalletService walletService;

	@Override
	public ResponseEntity<?> addMoney(Map<String, String> requestMap) {
		try {
			return walletService.addMoney(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getAmountByUserId(String userId) {
		try {
			return walletService.getAmountByUserId(userId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
