package com.company.test.IotRestProject.utils;

public class DeviceErrorResponse {
	private String message;
	private long timestamp;
	public DeviceErrorResponse(String message, long timestamp) {
		super();
		this.message = message;
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
