package com.company.test.IotRestProject.services;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.test.IotRestProject.models.Device;
import com.company.test.IotRestProject.repositories.DevicesRepository;
import com.company.test.IotRestProject.utils.DeviceNotFoundException;
import com.company.test.IotRestProject.utils.JWTUtil;

@Service
@Transactional(readOnly = true)
public class DeviceService {
	private final DevicesRepository devicesRepository;
	private final JWTUtil jwtUtil;

	@Autowired
	public DeviceService(DevicesRepository devicesRepository,JWTUtil jwtUtil) {
		super();
		this.devicesRepository = devicesRepository;
		this.jwtUtil = jwtUtil;
	}
	public Device enrich(Device device) {
		device.setAddedAt(LocalDateTime.now());
		device.setSecretKey(jwtUtil.generateToken(device.getSerialNumber()));
		return device;
	}
	@Transactional
	public String register(Device device) {
		devicesRepository.save(device);
		return "jwt_token: " + device.getSecretKey();
	}
	public Device findBySerialNumber(String serialNumber) {
		return devicesRepository.findBySerialNumber(serialNumber).orElseThrow(DeviceNotFoundException::new);
	}
	
}
