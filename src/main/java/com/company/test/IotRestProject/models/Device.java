package com.company.test.IotRestProject.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "\"Device\"")
public class Device {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "serial_number")
	private String serialNumber;
	@Column(name = "name")
	private String name;
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private TypeOfDevice type;
	@Column(name = "secret_key")
	private String secretKey;
	@Column(name = "added_at")
	private LocalDateTime addedAt;
	@OneToOne(mappedBy = "deviceId")
	private ActiveDevice isActiveId;
	@OneToMany(mappedBy = "deviceId")
	private List<Event> events;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public TypeOfDevice getType() {
		return type;
	}
	public void setType(TypeOfDevice type) {
		this.type = type;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public LocalDateTime getAddedAt() {
		return addedAt;
	}
	public void setAddedAt(LocalDateTime addedAt) {
		this.addedAt = addedAt;
	}
	public ActiveDevice getIsActiveId() {
		return isActiveId;
	}
	public void setIsActiveId(ActiveDevice isActiveId) {
		this.isActiveId = isActiveId;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	
	
	
	
	
}
