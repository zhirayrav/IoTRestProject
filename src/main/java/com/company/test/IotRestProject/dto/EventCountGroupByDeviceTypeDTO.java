package com.company.test.IotRestProject.dto;

import com.company.test.IotRestProject.models.TypeOfDevice;

public class EventCountGroupByDeviceTypeDTO {
	private TypeOfDevice type;
	private Long eventCount;
	
	public EventCountGroupByDeviceTypeDTO() {
		super();
	}
	public EventCountGroupByDeviceTypeDTO(TypeOfDevice type, Long eventCount) {
		super();
		this.type = type;
		this.eventCount = eventCount;
	}
	public TypeOfDevice getType() {
		return type;
	}
	public void setType(TypeOfDevice type) {
		this.type = type;
	}
	public Long getEventCount() {
		return eventCount;
	}
	public void setEventCount(Long eventCount) {
		this.eventCount = eventCount;
	}
	
	
}
