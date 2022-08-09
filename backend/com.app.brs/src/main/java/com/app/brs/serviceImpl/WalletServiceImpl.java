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

import com.app.brs.POJO.Wallet;
import com.app.brs.constant.ReservationSystemConstant;
import com.app.brs.dao.WalletDao;
import com.app.brs.service.WalletService;
import com.app.brs.utils.ReservationSystemUtils;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	WalletDao walletDao;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> addMoney(Map<String, String> requestMap) {
		try {
			if (requestMap.containsKey(ReservationSystemConstant.USER_ID)
					&& requestMap.containsKey(ReservationSystemConstant.AMOUNT)) {
				Wallet wallet = walletDao.getWalletByUserId(requestMap.get(ReservationSystemConstant.USER_ID));
				if (Objects.isNull(wallet)) {
					Wallet newWallet = new Wallet();
					newWallet.setId(ReservationSystemUtils.getId(ReservationSystemConstant.WALLET_ID_PREFIX));
					newWallet.setAmount(requestMap.get(ReservationSystemConstant.AMOUNT));
					newWallet.setUserId(requestMap.get(ReservationSystemConstant.USER_ID));
					walletDao.insert(newWallet);
				} else {
					Query query = new Query();
					query.addCriteria(Criteria.where(ReservationSystemConstant.USER_ID)
							.in(requestMap.get(ReservationSystemConstant.USER_ID)));
					Update update = new Update();
					String oldAmount = wallet.getAmount();
					int totalAmount = Integer.parseInt(oldAmount)
							+ Integer.parseInt(requestMap.get(ReservationSystemConstant.AMOUNT));
					System.out.println("Total Amount" + totalAmount);
					update.set(ReservationSystemConstant.AMOUNT, String.valueOf(totalAmount));
					mongoTemplate.updateFirst(query, update, Wallet.class);
				}
				return new ResponseEntity<>("{\"message\":\"Amount added to wallet.\"}", HttpStatus.OK);
			} else
				return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getAmountByUserId(String userId) {
		try {
			Wallet wallet = walletDao.getWalletByUserId(userId);
			if (Objects.isNull(wallet)) {
				wallet = new Wallet();
				wallet.setAmount("0");
			}
			return new ResponseEntity<>(wallet, HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
