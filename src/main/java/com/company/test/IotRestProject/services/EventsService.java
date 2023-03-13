package com.company.test.IotRestProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.test.IotRestProject.models.ActiveDevice;
import com.company.test.IotRestProject.models.Event;
import com.company.test.IotRestProject.models.Payload;
import com.company.test.IotRestProject.repositories.EventRepository;
import com.company.test.IotRestProject.repositories.PayloadRepository;

@Service
@Transactional(readOnly = true)
public class EventsService {
	private final EventRepository eventRepository;
	private final ActiveDevicesService activeDevicesService;
	private final PayloadRepository payloadRepository;
	@Autowired
	public EventsService(EventRepository eventRepository,ActiveDevicesService activeDevicesService,PayloadRepository payloadRepository) {
		super();
		this.eventRepository = eventRepository;
		this.activeDevicesService = activeDevicesService;
		this.payloadRepository = payloadRepository;
	}
	@Transactional
	public void register(Event event) {
		Payload payload = event.getPayload();
		payloadRepository.save(payload);
		eventRepository.save(event);
		ActiveDevice activDevice = activeDevicesService.findByDeviceId(event.getDevice());
		if(activDevice == null) {
			ActiveDevice newActiveDevice = new ActiveDevice();
			newActiveDevice.setFirstActivity(event.getHappenedAt());
			newActiveDevice.setLastActivity(event.getHappenedAt());
			newActiveDevice.setDevice(event.getDevice());
			activeDevicesService.saveActivity(newActiveDevice);
		}
		else
			activDevice.setLastActivity(event.getHappenedAt());
		
	}
}
