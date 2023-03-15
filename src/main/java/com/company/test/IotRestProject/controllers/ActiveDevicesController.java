package com.company.test.IotRestProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.test.IotRestProject.models.Device;
import com.company.test.IotRestProject.services.ActiveDevicesService;
import com.company.test.IotRestProject.services.DeviceService;

@RestController
@RequestMapping("/active_device")
public class ActiveDevicesController {
	public final ActiveDevicesService activeDevicesService;
	public final DeviceService deviceService;
	@Autowired
	public ActiveDevicesController(ActiveDevicesService activeDevicesService,DeviceService deviceService) {
		super();
		this.activeDevicesService = activeDevicesService;
		this.deviceService = deviceService;
	}
	@GetMapping()
	public List<Device> getAllActiveDevices(){
		return deviceService.getAllActiveDevices();
	}
	
	
}
