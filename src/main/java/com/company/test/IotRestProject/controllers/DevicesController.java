package com.company.test.IotRestProject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.company.test.IotRestProject.dto.DeviceDTO;
import com.company.test.IotRestProject.models.Device;
import com.company.test.IotRestProject.models.TypeOfDevice;
import com.company.test.IotRestProject.services.DeviceService;
import com.company.test.IotRestProject.utils.JWTUtil;
import com.company.test.IotRestProject.utils.TypeOfDeviceErrorResponse;
import com.company.test.IotRestProject.utils.TypeOfDeviceNotFoundException;

@RestController
@RequestMapping("/device")
public class DevicesController {
	private final DeviceService deviceService;
	private final JWTUtil jwtUtil;
	private final ModelMapper modelMapper;
	
	@Autowired
	public DevicesController(DeviceService deviceService, JWTUtil jwtUtil,ModelMapper modelMapper) {
		super();
		this.deviceService = deviceService;
		this.jwtUtil = jwtUtil;
		this.modelMapper = modelMapper;
	}
	public Device convertToDevice(DeviceDTO deviceDTO) {
		Device device = modelMapper.map(deviceDTO, Device.class);
		device = deviceService.enrich(device);
		if(deviceDTO.getType().toUpperCase().equals("BLUETOOTH"))
			device.setType(TypeOfDevice.BLUETOOTH);
		else if(deviceDTO.getType().toUpperCase().equals("WI_FI"))
			device.setType(TypeOfDevice.WI_FI);
		else throw new TypeOfDeviceNotFoundException();
		return device;
			
		
		
	}
	@PostMapping("/registration")
	public String performRegistration(@RequestBody DeviceDTO deviceDTO) {
		Device device = convertToDevice(deviceDTO);
		return deviceService.register(device);
		
	}
	@ExceptionHandler
	private ResponseEntity<TypeOfDeviceErrorResponse> handleException(TypeOfDeviceNotFoundException e){
		TypeOfDeviceErrorResponse response = new TypeOfDeviceErrorResponse("This type of device wasn't found!", System.currentTimeMillis());
		return new ResponseEntity<TypeOfDeviceErrorResponse>(response,HttpStatus.NOT_FOUND);
	}
	
}
