package com.app.brs.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.brs.POJO.Reservation;
import com.app.brs.POJO.Wallet;
import com.app.brs.constant.ReservationSystemConstant;
import com.app.brs.dao.AppUserDao;
import com.app.brs.dao.BusDao;
import com.app.brs.dao.ReservationDao;
import com.app.brs.dao.WalletDao;
import com.app.brs.service.ReservationService;
import com.app.brs.utils.ReservationSystemUtils;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	ReservationDao reservationDao;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	BusDao busDao;

	@Autowired
	AppUserDao appUserDao;

	@Autowired
	WalletDao walletDao;

	@Override
	public ResponseEntity<?> reserveSeat(Map<String, Object> requestMap) {
		try {
			if (validateReserveSeatMap(requestMap)) {
				Wallet wallet = walletDao.getWalletByUserId((String) requestMap.get(ReservationSystemConstant.USER_ID));
				if (!Objects.isNull(wallet)
						&& validateWalletAmount(wallet, (String) requestMap.get(ReservationSystemConstant.FARE))) {
					Reservation reservation = new Reservation();
					reservation.setId(ReservationSystemUtils.getId(ReservationSystemConstant.RESERVATION_PREFIX));
					reservation
							.setBus((busDao.findById((String) requestMap.get(ReservationSystemConstant.BUS_ID))).get());
					reservation.setAppUser(
							(appUserDao.findById((String) requestMap.get(ReservationSystemConstant.USER_ID))).get());
					reservation.setSeats((String) requestMap.get(ReservationSystemConstant.SEATS));
					reservation.setJournyDate((String) requestMap.get(ReservationSystemConstant.JOURNY_DATE));
					reservation.setStatus(ReservationSystemConstant.BOOKED);
					reservation.setFare((String) requestMap.get(ReservationSystemConstant.FARE));
					updateWalletAmount(wallet, (String) requestMap.get(ReservationSystemConstant.FARE), true);
					reservationDao.insert(reservation);
					return new ResponseEntity<>("{\"message\":\"Seat reserved.\"}", HttpStatus.OK);
				} else
					return new ResponseEntity<>("{\"message\":\"Insufficent Fund.\"}", HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateWalletAmount(Wallet wallet, String fare) {
		return Integer.parseInt(wallet.getAmount()) >= Integer.parseInt(fare);
	}

	private void updateWalletAmount(Wallet wallet, String amountToSubtract, boolean toSubtract) {
		Query query = new Query();
		query.addCriteria(Criteria.where(ReservationSystemConstant.USER_ID).in(wallet.getUserId()));
		Update update = new Update();
		String oldAmount = wallet.getAmount();
		int totalAmount;
		if (toSubtract)
			totalAmount = Integer.parseInt(oldAmount) - Integer.parseInt(amountToSubtract);
		else
			totalAmount = Integer.parseInt(oldAmount) + Integer.parseInt(amountToSubtract);
		System.out.println("Total Amount" + totalAmount);
		update.set(ReservationSystemConstant.AMOUNT, String.valueOf(totalAmount));
		mongoTemplate.updateFirst(query, update, Wallet.class);
	}

	private boolean validateReserveSeatMap(Map<String, Object> requestMap) {
		return requestMap.containsKey(ReservationSystemConstant.BUS_ID)
				&& requestMap.containsKey(ReservationSystemConstant.USER_ID)
				&& requestMap.containsKey(ReservationSystemConstant.SEATS)
				&& requestMap.containsKey(ReservationSystemConstant.JOURNY_DATE)
				&& requestMap.containsKey(ReservationSystemConstant.FARE);
	}

	@Override
	public ResponseEntity<?> getReservationByUserId(String userId, String status) {
		return new ResponseEntity<>(reservationDao.getReservationByUserId(userId, status), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getBookedSeats(Map<String, String> requestMap) {
		try {
			if (requestMap.containsKey(ReservationSystemConstant.BUS_ID)
					&& requestMap.containsKey(ReservationSystemConstant.DATE)) {
				List<Reservation> reservationList = reservationDao.getBookedSeatsByIdAndDate(
						requestMap.get(ReservationSystemConstant.BUS_ID),
						requestMap.get(ReservationSystemConstant.DATE), ReservationSystemConstant.BOOKED);
				if (!Objects.isNull(reservationList) && reservationList.size() > 0) {
					List<String> seatsList = reservationList.stream().map(Reservation::getSeats)
							.collect(Collectors.toList());
					String allSeat = String.join(",", seatsList);
					int[] numbers = Arrays.stream(allSeat.split(",")).mapToInt(Integer::parseInt).toArray();
					return new ResponseEntity<>(numbers, HttpStatus.OK);
				}
				return new ResponseEntity<>(new int[] {}, HttpStatus.OK);
			} else
				return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> cancleBooking(String reservationId) {
		try {
			if (!com.google.common.base.Strings.isNullOrEmpty(reservationId)) {
				Query query = new Query();
				query.addCriteria(Criteria.where(ReservationSystemConstant.ID).in(reservationId));
				Update update = new Update();
				update.set(ReservationSystemConstant.STATUS, ReservationSystemConstant.CANCELLED);
				mongoTemplate.updateFirst(query, update, Reservation.class);
				refundFromCanellation(reservationId);
				return new ResponseEntity<>("{\"message\":\"Reservation with id " + reservationId + " Cancelled.\"}",
						HttpStatus.OK);
			} else
				return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void refundFromCanellation(String reservationId) {
		Reservation reservation = reservationDao.getReservationByReservationId(reservationId);
		Wallet wallet = walletDao.getWalletByUserId(reservation.getAppUser().getId());
		updateWalletAmount(wallet, reservation.getFare(), false);
	}

}
