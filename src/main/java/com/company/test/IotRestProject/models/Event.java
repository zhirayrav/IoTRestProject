package com.company.test.IotRestProject.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonRawValue;

@Entity
@Table(name = "\"Event\"")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@ManyToOne
	@JoinColumn(name = "device_id",referencedColumnName = "id")
	@JsonBackReference(value = "device-event")
	private Device device;
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private TypeOfEvent type;
	@Column(name = "happened_at")
	private LocalDateTime happenedAt;
	@OneToOne
	@JoinColumn(name = "payload_id",referencedColumnName = "id")
	@JsonManagedReference(value = "event-payload")
	private Payload payload;
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
	public TypeOfEvent getType() {
		return type;
	}
	public void setType(TypeOfEvent type) {
		this.type = type;
	}
	public LocalDateTime getHappenedAt() {
		return happenedAt;
	}
	public void setHappenedAt(LocalDateTime happenedAt) {
		this.happenedAt = happenedAt;
	}
	public Payload getPayload() {
		return payload;
	}
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	
	
	
}
