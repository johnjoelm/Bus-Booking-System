package com.app.brs.serviceImpl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.brs.POJO.AppUser;
import com.app.brs.constant.ReservationSystemConstant;
import com.app.brs.dao.AppUserDao;
import com.app.brs.service.AppUserService;
import com.app.brs.utils.ReservationSystemUtils;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	AppUserDao appUserDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> signUp(Map<String, String> requestMap) {
		try {
			if (validateSignUpMap(requestMap)) {
				AppUser user = appUserDao.getUserByUserName(requestMap.get(ReservationSystemConstant.USER_NAME));
				if (Objects.isNull(user)) {
					AppUser appUser = getAppUserFromRequestMap(requestMap);
					appUserDao.insert(appUser);
					return new ResponseEntity<>("{\"message\":\"SignUp Successful.\"}", HttpStatus.OK);
				} else
					return new ResponseEntity<>("{\"message\":\"Try with different Username.\"}",
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private AppUser getAppUserFromRequestMap(Map<String, String> requestMap) {
		AppUser appUser = new AppUser();
		appUser.setId(ReservationSystemUtils.getId(ReservationSystemConstant.APP_USER_ID_PREFIX));
		appUser.setFullName(requestMap.get(ReservationSystemConstant.FULL_NAME));
		appUser.setEmail(requestMap.get(ReservationSystemConstant.EMAIL));
		appUser.setPassword(requestMap.get(ReservationSystemConstant.PASSWORD));
		appUser.setUsername(requestMap.get(ReservationSystemConstant.USER_NAME));
		appUser.setPhone(requestMap.get(ReservationSystemConstant.PHONE));
		appUser.setRole(
				requestMap.containsKey(ReservationSystemConstant.ROLE) ? requestMap.get(ReservationSystemConstant.ROLE)
						: ReservationSystemConstant.USER);
		return appUser;
	}

	private boolean validateSignUpMap(Map<String, String> requestMap) {
		return requestMap.containsKey(ReservationSystemConstant.PHONE)
				&& requestMap.containsKey(ReservationSystemConstant.USER_NAME)
				&& requestMap.containsKey(ReservationSystemConstant.PASSWORD)
				&& requestMap.containsKey(ReservationSystemConstant.FULL_NAME)
				&& requestMap.containsKey(ReservationSystemConstant.EMAIL);
	}

	@Override
	public ResponseEntity<?> logIn(Map<String, String> requestMap) {
		try {
			if (requestMap.containsKey(ReservationSystemConstant.USER_NAME)
					&& requestMap.containsKey(ReservationSystemConstant.PASSWORD)) {
				AppUser appUser = appUserDao.logIn(requestMap.get(ReservationSystemConstant.USER_NAME),
						requestMap.get(ReservationSystemConstant.PASSWORD));
				if (!Objects.isNull(appUser)) {
					appUser.setPassword(null);
					return new ResponseEntity<>(appUser, HttpStatus.OK);
				} else
					return new ResponseEntity<>("{\"message\":\"Bad Credentials.\"}", HttpStatus.UNAUTHORIZED);
			} else
				return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> changePassword(Map<String, String> requestMap) {
		try {
			if (requestMap.containsKey(ReservationSystemConstant.USER_NAME)
					&& requestMap.containsKey(ReservationSystemConstant.PASSWORD)
					&& requestMap.containsKey(ReservationSystemConstant.OLD_PASSWORD)) {
				AppUser appUser = appUserDao.getUserByUserName(requestMap.get(ReservationSystemConstant.USER_NAME));
				if (requestMap.get(ReservationSystemConstant.OLD_PASSWORD).equals(appUser.getPassword())) {
					changePassword(requestMap.get(ReservationSystemConstant.USER_NAME),
							requestMap.get(ReservationSystemConstant.PASSWORD));
					return new ResponseEntity<>("{\"message\":\"Password Changed Successfully.\"}", HttpStatus.OK);
				} else
					return new ResponseEntity<>("{\"message\":\"Wrong old password.\"}", HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void changePassword(String username, String password) {
		Query query = new Query();
		query.addCriteria(Criteria.where(ReservationSystemConstant.USER_NAME).in(username));
		Update update = new Update();
		update.set(ReservationSystemConstant.PASSWORD, password);
		mongoTemplate.updateMulti(query, update, AppUser.class);
	}

}
