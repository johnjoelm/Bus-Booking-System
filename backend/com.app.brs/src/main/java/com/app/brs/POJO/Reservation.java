package com.app.brs.POJO;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Reservation")
public class Reservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

//	@DBRef(db = "AppUser")
	private AppUser appUser;

//	@DBRef(db = "Bus")
	private Bus bus;

	private String seats;

	private String journyDate;

	private String status;

	private String fare;

	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reservation(String id, AppUser appUser, Bus bus, String seats, String journyDate, String status,
			String fare) {
		super();
		this.id = id;
		this.appUser = appUser;
		this.bus = bus;
		this.seats = seats;
		this.journyDate = journyDate;
		this.status = status;
		this.fare = fare;
	}

	public String getFare() {
		return fare;
	}

	public void setFare(String fare) {
		this.fare = fare;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public String getJournyDate() {
		return journyDate;
	}

	public void setJournyDate(String journyDate) {
		this.journyDate = journyDate;
	}

}
