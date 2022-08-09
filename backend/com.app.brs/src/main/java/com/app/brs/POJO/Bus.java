package com.app.brs.POJO;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Bus")
public class Bus implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String busName;

	private String day;

	private String from;

	private String to;

	private String departTime;

	private String arivalTime;

	private String chargePerSeat;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public String getArivalTime() {
		return arivalTime;
	}

	public void setArivalTime(String arivalTime) {
		this.arivalTime = arivalTime;
	}

	public String getChargePerSeat() {
		return chargePerSeat;
	}

	public void setChargePerSeat(String chargePerSeat) {
		this.chargePerSeat = chargePerSeat;
	}

	public Bus() {
		super();
	}

	public Bus(String busName, String day, String from, String to, String departTime, String arivalTime,
			String chargePerSeat) {
		super();
		this.busName = busName;
		this.day = day;
		this.from = from;
		this.to = to;
		this.departTime = departTime;
		this.arivalTime = arivalTime;
		this.chargePerSeat = chargePerSeat;
	}

}
