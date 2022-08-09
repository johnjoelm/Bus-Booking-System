package com.app.brs.POJO;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Wallet")
public class Wallet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String amount;

	private String userId;

	public Wallet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Wallet(String id, String amount, String userId) {
		super();
		this.id = id;
		this.amount = amount;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
