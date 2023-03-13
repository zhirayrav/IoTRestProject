package com.company.test.IotRestProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.test.IotRestProject.models.ActiveDevice;
import com.company.test.IotRestProject.models.Device;
import com.company.test.IotRestProject.repositories.ActiveDevicesRepository;

@Service
@Transactional(readOnly = true)
public class ActiveDevicesService {
	private final ActiveDevicesRepository activeDevicesRepository;
	@Autowired
	public ActiveDevicesService(ActiveDevicesRepository activeDevicesRepository) {
		super();
		this.activeDevicesRepository = activeDevicesRepository;
	}
	public ActiveDevice findByDeviceId(Device device) {
		return activeDevicesRepository.findByDeviceId(device).orElse(null);
	}
	@Transactional
	public void saveActivity(ActiveDevice activeDevice) {
		activeDevicesRepository.save(activeDevice);
	}
	
}
