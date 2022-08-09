package com.app.brs.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.brs.POJO.Bus;
import com.app.brs.constant.ReservationSystemConstant;
import com.app.brs.dao.BusDao;
import com.app.brs.service.BusService;
import com.app.brs.utils.ReservationSystemUtils;
import com.mongodb.client.result.UpdateResult;

@Service
public class BusServiceImpl implements BusService {

	@Autowired
	BusDao busDao;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> addBus(Map<String, String> requestMap) {
		try {
			if (validateAddBusMap(requestMap)) {
				Bus bus = getBusFromRequestMap(requestMap);
				busDao.insert(bus);
				return new ResponseEntity<>("{\"message\":\"Bus Added Successfully.\"}", HttpStatus.OK);
			} else
				return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private Bus getBusFromRequestMap(Map<String, String> requestMap) {
		Bus bus = new Bus();
		bus.setId(ReservationSystemUtils.getId(ReservationSystemConstant.BUS_ID_PREFIX));
		bus.setBusName(requestMap.get(ReservationSystemConstant.BUS_NAME));
		bus.setDay(requestMap.get(ReservationSystemConstant.DAY));
		bus.setFrom(requestMap.get(ReservationSystemConstant.FROM));
		bus.setTo(requestMap.get(ReservationSystemConstant.TO));
		bus.setDepartTime(requestMap.get(ReservationSystemConstant.DEPART_TIME));
		bus.setArivalTime(requestMap.get(ReservationSystemConstant.ARIVAL_TIME));
		bus.setChargePerSeat(requestMap.get(ReservationSystemConstant.CHARGE_PER_SEAT));
		return bus;
	}

	private boolean validateAddBusMap(Map<String, String> requestMap) {
		return requestMap.containsKey(ReservationSystemConstant.ARIVAL_TIME)
				&& requestMap.containsKey(ReservationSystemConstant.DEPART_TIME)
				&& requestMap.containsKey(ReservationSystemConstant.TO)
				&& requestMap.containsKey(ReservationSystemConstant.FROM)
				&& requestMap.containsKey(ReservationSystemConstant.DAY)
				&& requestMap.containsKey(ReservationSystemConstant.BUS_NAME)
				&& requestMap.containsKey(ReservationSystemConstant.CHARGE_PER_SEAT);
	}

	@Override
	public ResponseEntity<?> getAllBus() {
		try {
			return new ResponseEntity<>(busDao.findAll(), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getAllBusByDate(Map<String, String> requestMap) {
		try {
			if (requestMap.containsKey(ReservationSystemConstant.DATE)
					&& requestMap.containsKey(ReservationSystemConstant.FROM)
					&& requestMap.containsKey(ReservationSystemConstant.TO)) {
				String day = ReservationSystemUtils.getDayFromDate(requestMap.get(ReservationSystemConstant.DATE));
				return new ResponseEntity<>(busDao.getBusByDay(day, requestMap.get(ReservationSystemConstant.FROM),
						requestMap.get(ReservationSystemConstant.TO)), HttpStatus.OK);
			} else
				return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> updateBus(Map<String, String> requestMap) {
		System.out.println(requestMap);
		try {
			if (validateAddBusMap(requestMap) && requestMap.containsKey(ReservationSystemConstant.ID)) {
				Query query = new Query();
				System.out.println(requestMap.get(ReservationSystemConstant.ID));
				query.addCriteria(
						Criteria.where(ReservationSystemConstant.ID).in(requestMap.get(ReservationSystemConstant.ID)));
				Update update = new Update();
				update.set(ReservationSystemConstant.ARIVAL_TIME,
						requestMap.get(ReservationSystemConstant.ARIVAL_TIME));
				update.set(ReservationSystemConstant.DEPART_TIME,
						requestMap.get(ReservationSystemConstant.DEPART_TIME));
				update.set(ReservationSystemConstant.TO, requestMap.get(ReservationSystemConstant.TO));
				update.set(ReservationSystemConstant.FROM, requestMap.get(ReservationSystemConstant.FROM));
				update.set(ReservationSystemConstant.DAY, requestMap.get(ReservationSystemConstant.DAY));
				update.set(ReservationSystemConstant.BUS_NAME, requestMap.get(ReservationSystemConstant.BUS_NAME));
				update.set(ReservationSystemConstant.CHARGE_PER_SEAT,
						requestMap.get(ReservationSystemConstant.CHARGE_PER_SEAT));
				UpdateResult s = mongoTemplate.updateMulti(query, update, Bus.class);
				if (s.getModifiedCount() > 0)
					return new ResponseEntity<>("{\"message\":\"Bus with id "
							+ requestMap.get(ReservationSystemConstant.ID) + " updated.\"}", HttpStatus.OK);
				else
					return new ResponseEntity<>("{\"message\":\"Bus with id "
							+ requestMap.get(ReservationSystemConstant.ID) + " updation failed.\"}",
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("{\"message\":\"Something Went Wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
