package com.company.test.IotRestProject.controllers;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

import org.modelmapper.ModelMapper;
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
import com.company.test.IotRestProject.dto.DeviceDTO;
import com.company.test.IotRestProject.models.Device;
import com.company.test.IotRestProject.models.TypeOfDevice;
import com.company.test.IotRestProject.services.ActiveDevicesService;
import com.company.test.IotRestProject.services.DeviceService;
import com.company.test.IotRestProject.services.EventsService;
import com.company.test.IotRestProject.utils.TypeOfDeviceErrorResponse;
import com.company.test.IotRestProject.utils.TypeOfDeviceNotFoundException;

@RestController
@RequestMapping("/device")
public class DevicesController {
	private final DeviceService deviceService;
	private final ModelMapper modelMapper;
	@Autowired
	public DevicesController(DeviceService deviceService, EventsService eventsService,ModelMapper modelMapper,ActiveDevicesService activeDevicesService) {
		super();
		this.deviceService = deviceService;
		this.modelMapper = modelMapper;
	}
	public Device convertToDevice(DeviceDTO deviceDTO) {
		Device device = modelMapper.map(deviceDTO, Device.class);
		device = deviceService.enrichForSave(device);
		if(deviceDTO.getType().toUpperCase().equals("BLUETOOTH"))
			device.setType(TypeOfDevice.BLUETOOTH);
		else if(deviceDTO.getType().toUpperCase().equals("WI-FI"))
			device.setType(TypeOfDevice.WI_FI);
		else throw new TypeOfDeviceNotFoundException();
		return device;
	}
	@PostMapping("/registration")
	public String performRegistration(@RequestBody DeviceDTO deviceDTO) {
		Device device = convertToDevice(deviceDTO);
		return deviceService.register(device);
		
	}
	@GetMapping("/{serialNumber}")
	public Device getDeviceBySerialNumber(@PathVariable String serialNumber) {
		Device device = deviceService.findBySerialNumber(serialNumber);
		return device;
	}
	@GetMapping()
	public List<Device> getAllDevices(@RequestParam(value = "page",required = false) Integer page,
			@RequestParam(value = "devicesPerPage",required = false) Integer devicesPerPage){
		if(page == null || devicesPerPage == null) {
			return deviceService.getAllDevices();
		}
		else {
			return deviceService.findAllWithPagination(page, devicesPerPage);
		}
		
	}
	@GetMapping("/filterByType")
	public List<Device> filterByType(@RequestParam("type") String type){
		TypeOfDevice typeOfDevice = EnumSet.allOf(TypeOfDevice.class)
				.stream().filter(t -> t.name().equals(type.toUpperCase()))
				.findAny().orElseThrow(TypeOfDeviceNotFoundException::new);
		return deviceService.getDevicesByType(typeOfDevice);
	}
	@GetMapping("/filterByDate")
	public List<Device> filterByDate(@RequestParam(value = "from",required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime from,
			@RequestParam(value = "to",required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime to){
		if(from == null && to !=null)
			return deviceService.getDevicesAddedBefore(to);
		else if(to == null && from !=null)
			return deviceService.getDevicesAddedAfter(from);
		else if(from != null && to != null)
			return deviceService.getDevicesAddedBetween(from, to);
		else 
			return deviceService.getAllDevices();
	}
	@ExceptionHandler
	private ResponseEntity<TypeOfDeviceErrorResponse> handleException(TypeOfDeviceNotFoundException e){
		TypeOfDeviceErrorResponse response = new TypeOfDeviceErrorResponse("This type of device wasn't found!", System.currentTimeMillis());
		return new ResponseEntity<TypeOfDeviceErrorResponse>(response,HttpStatus.NOT_FOUND);
	}
	
}
