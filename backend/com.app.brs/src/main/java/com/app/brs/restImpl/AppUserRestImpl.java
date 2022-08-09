package com.app.brs.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.brs.rest.AppUserRest;
import com.app.brs.service.AppUserService;

@RestController
public class AppUserRestImpl implements AppUserRest {

	@Autowired
	AppUserService appUserService;

	@Override
	public ResponseEntity<?> signUp(Map<String, String> requestMap) {
		try {
			return appUserService.signUp(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> logIn(Map<String, String> requestMap) {
		try {
			return appUserService.logIn(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> changePassword(Map<String, String> requestMap) {
		try {
			return appUserService.changePassword(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
