package com.company.test.IotRestProject.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.test.IotRestProject.dto.EventDTO;
import com.company.test.IotRestProject.models.Device;
import com.company.test.IotRestProject.models.Event;
import com.company.test.IotRestProject.models.TypeOfEvent;
import com.company.test.IotRestProject.services.DeviceService;
import com.company.test.IotRestProject.services.EventsService;
import com.company.test.IotRestProject.utils.DeviceErrorResponse;
import com.company.test.IotRestProject.utils.DeviceNotFoundException;
import com.company.test.IotRestProject.utils.JWTUtil;
import com.company.test.IotRestProject.utils.TypeOfEventErrorResponse;
import com.company.test.IotRestProject.utils.TypeOfEventNotFoundException;

@RestController
@RequestMapping("/event")
public class EventsController {
	private final EventsService eventsService;
	private final DeviceService deviceService;
	private final JWTUtil jwtUtil;
	@Autowired
	public EventsController(EventsService eventsService,JWTUtil jwtUtil,DeviceService deviceService) {
		super();
		this.eventsService = eventsService;
		this.jwtUtil = jwtUtil;
		this.deviceService = deviceService;
	}
	
	@PostMapping("/registration")
	public void performRegistration(@RequestBody EventDTO eventDTO) {
		Event event = convertToEvent(eventDTO);
		eventsService.register(event);
		
	}
	public Event convertToEvent(EventDTO eventDTO) {
		
		String serialNumber = jwtUtil.validateTokenAndRetrieveClaim(eventDTO.getSecretKey());
		Device device = deviceService.findBySerialNumber(serialNumber);
		Event event = new Event();
		event.setDevice(device);
		event.setHappenedAt(LocalDateTime.now());
		if(eventDTO.getType().toUpperCase().equals("MOTION"))
			event.setType(TypeOfEvent.MOTION);
		else if(eventDTO.getType().toUpperCase().equals("WEATHER"))
			event.setType(TypeOfEvent.WEATHER);
		else throw new TypeOfEventNotFoundException();
		event.setPayload(eventDTO.getPayload()); 
		return event;	
	}
	@ExceptionHandler
	private ResponseEntity<DeviceErrorResponse> handleException(DeviceNotFoundException e){
		DeviceErrorResponse response = new DeviceErrorResponse("Device with this secret key wasn't found!",System.currentTimeMillis());
		return new ResponseEntity<DeviceErrorResponse>(response,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	private ResponseEntity<TypeOfEventErrorResponse> handleException(TypeOfEventNotFoundException e){
		TypeOfEventErrorResponse response = new TypeOfEventErrorResponse("This type of event wasn't found!",System.currentTimeMillis());
		return new ResponseEntity<TypeOfEventErrorResponse>(response, HttpStatus.NOT_FOUND);
	}
}
