package com.company.test.IotRestProject.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "\"Active_device\"")
public class ActiveDevice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@OneToOne
	@JoinColumn(name = "device_id",referencedColumnName = "id")
	@JsonBackReference(value = "devise-actDevice")   
	private Device device;
	@Column(name = "first_activity")
	private LocalDateTime firstActivity;
	@Column(name = "last_activity")
	private LocalDateTime lastActivity;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public LocalDateTime getFirstActivity() {
		return firstActivity;
	}
	public void setFirstActivity(LocalDateTime firstActivity) {
		this.firstActivity = firstActivity;
	}
	public LocalDateTime getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(LocalDateTime lastActivity) {
		this.lastActivity = lastActivity;
	}
	
	
}
