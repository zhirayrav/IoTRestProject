package com.company.test.IotRestProject.controllers;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.test.IotRestProject.dto.EventCountGroupByDeviceTypeDTO;
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
	public ResponseEntity<HttpStatus> performRegistration(@RequestBody EventDTO eventDTO) {
		Event event = convertToEvent(eventDTO);
		eventsService.register(event);
		return ResponseEntity.ok(HttpStatus.OK);
		
	}
	@GetMapping("/{serialNumber}")
	public List<Event> getEventsByDeviceSerialNumber(@PathVariable String serialNumber,
			@RequestParam(value = "page",required = false) Integer page,
			@RequestParam(value = "eventsPerPage",required = false) Integer eventsPerPage){
		if(page != null && eventsPerPage != null) {
			return eventsService.findEventsByDeviceSerialNumberWithPagination(serialNumber, page, eventsPerPage);
		}else		
			return eventsService.findEventsByDeviceSerialNumber(serialNumber);
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
	@GetMapping("/{serialNumber}/filterByDate")
	public List<Event> getEventsWithFilterByDate(@PathVariable("serialNumber") String serialNumber,
			@RequestParam(value = "from",required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime from,
			@RequestParam(value = "to",required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime to){
			if(from == null && to !=null)
				return eventsService.findBySerialNumberBefore(serialNumber, to);
			else if(to == null && from !=null)
				return eventsService.findBySerialNumberAfter(serialNumber, from);
			else if(from != null && to != null)
				return eventsService.findBySerialNumberBetween(serialNumber, from, to);
			else 
				return eventsService.findByDevice(deviceService.findBySerialNumber(serialNumber));
	}
	@GetMapping("/count")
	public List<EventCountGroupByDeviceTypeDTO> getEventsCountOrderByDeviceOfType(@RequestParam(value = "from",required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime from,
			@RequestParam(value = "to",required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime to){
		if(from != null && to != null) {
			return eventsService.getEventsCountGroupByDeviceTypeBetween(from, to);
		}
		else if(from == null && to != null)
			return eventsService.getEventsCountGroupByDeviceTypeBefore(to);
		else if(from != null && to == null)
			return eventsService.getEventsCountGroupByDeviceTypeAfter(from);
		else
			return eventsService.getEventsCountGroupByDeviceType();
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
