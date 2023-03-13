package com.company.test.IotRestProject.dto;

import com.company.test.IotRestProject.models.Payload;

public class EventDTO {
	private String secretKey;
	private String type;
	private Payload payload;
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Payload getPayload() {
		return payload;
	}
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	
	
	
}
