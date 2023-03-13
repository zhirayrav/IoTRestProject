package com.company.test.IotRestProject.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class DeviceDTO {
	private String serialNumber;
	
	private String name;
	@Enumerated(EnumType.STRING)
	private String type;
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
