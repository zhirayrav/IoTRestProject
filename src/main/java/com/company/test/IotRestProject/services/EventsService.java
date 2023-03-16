package com.company.test.IotRestProject.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.test.IotRestProject.dto.EventCountGroupByDeviceTypeDTO;
import com.company.test.IotRestProject.models.ActiveDevice;
import com.company.test.IotRestProject.models.Device;
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
	private final DeviceService deviceService;
	@Autowired
	public EventsService(EventRepository eventRepository,ActiveDevicesService activeDevicesService,
			PayloadRepository payloadRepository,DeviceService deviceService) {
		super();
		this.eventRepository = eventRepository;
		this.activeDevicesService = activeDevicesService;
		this.payloadRepository = payloadRepository;
		this.deviceService = deviceService;
	}
	@Transactional
	public void register(Event event) {
		Payload payload = event.getPayload();
		payloadRepository.save(payload);
		eventRepository.save(event);
		ActiveDevice activDevice = activeDevicesService.findByDevice(event.getDevice());
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
	public List<Event> findByDevice(Device device){
		return eventRepository.findByDevice(device);
	}
	public List<Event> findEventsByDeviceSerialNumber(String serialNumber){
		return deviceService.findBySerialNumber(serialNumber).getEvents();
		
	}
	public List<Event> findEventsByDeviceSerialNumberWithPagination(String serialNumber,Integer page,Integer eventsPerPage){
		Device device = deviceService.findBySerialNumber(serialNumber);
		return eventRepository.findByDevice(device,PageRequest.of(page, eventsPerPage));
	}
	public List<Event> findBySerialNumberBefore(String serialNumber,LocalDateTime to){
		return eventRepository.findByDeviseSerialNumberHappenedAtBefore(serialNumber, to);
	}
	public List<Event> findBySerialNumberAfter(String serialNumber,LocalDateTime from){
		return eventRepository.findByDeviseSerialNumberHappenedAtAfter(serialNumber, from);
	}
	public List<Event> findBySerialNumberBetween(String serialNumber,LocalDateTime from,LocalDateTime to){
		return eventRepository.findByDeviseSerialNumberHappenedAtBetween(serialNumber, from, to);
	}
	
	public List<EventCountGroupByDeviceTypeDTO> getEventsCountGroupByDeviceTypeBetween(LocalDateTime from,LocalDateTime to){
		return eventRepository.findCountGroupByDeviceTypeBetween(from, to);
	}
	public List<EventCountGroupByDeviceTypeDTO> getEventsCountGroupByDeviceTypeBefore(LocalDateTime to){
		return eventRepository.findCountGroupByDeviceTypeBefore(to);
	}
	public List<EventCountGroupByDeviceTypeDTO> getEventsCountGroupByDeviceTypeAfter(LocalDateTime from){
		return eventRepository.findCountGroupByDeviceTypeAfter(from);
	}
	public List<EventCountGroupByDeviceTypeDTO> getEventsCountGroupByDeviceType(){
		return eventRepository.findCountGroupByDeviceType();
	}
}
