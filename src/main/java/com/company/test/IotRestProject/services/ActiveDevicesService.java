package com.company.test.IotRestProject.services;

import java.util.List;

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
	public ActiveDevice findByDevice(Device device) {
		return activeDevicesRepository.findByDevice(device).orElse(null);
	}
	@Transactional
	public void saveActivity(ActiveDevice activeDevice) {
		activeDevicesRepository.save(activeDevice);
	}
	public List<ActiveDevice> findAll(){
		return activeDevicesRepository.findAll();
	}
	@Transactional
	public void deleteByActiveDeviceId(int id) {
		activeDevicesRepository.deleteById(id);
	}
	
}
