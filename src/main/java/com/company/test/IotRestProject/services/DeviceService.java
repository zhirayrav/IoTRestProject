package com.company.test.IotRestProject.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.test.IotRestProject.models.ActiveDevice;
import com.company.test.IotRestProject.models.Device;
import com.company.test.IotRestProject.models.TypeOfDevice;
import com.company.test.IotRestProject.repositories.ActiveDevicesRepository;
import com.company.test.IotRestProject.repositories.DevicesRepository;
import com.company.test.IotRestProject.utils.DeviceNotFoundException;
import com.company.test.IotRestProject.utils.JWTUtil;

@Service
@Transactional(readOnly = true)
public class DeviceService {
	private final DevicesRepository devicesRepository;
	private final JWTUtil jwtUtil;
	private final ActiveDevicesService activeDevicesService;
	private final EventsService eventsService;

	@Autowired
	public DeviceService(DevicesRepository devicesRepository,JWTUtil jwtUtil,ActiveDevicesService activeDevicesService,
			EventsService eventsService) {
		super();
		this.devicesRepository = devicesRepository;
		this.jwtUtil = jwtUtil;
		this.activeDevicesService = activeDevicesService;
		this.eventsService = eventsService;
	}
	@Transactional
	public Device enrichForSave(Device device) {
		device.setAddedAt(LocalDateTime.now());
		device.setSecretKey(jwtUtil.generateToken(device.getSerialNumber()));
		return device;
	}
	@Transactional
	public Device enrichForResponse(Device device) {
		device.setEvents(eventsService.findByDevice(device));
		device.setIsActiveId(activeDevicesService .findByDevice(device));
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
	@Transactional
	public List<Device> getAllActiveDevices() {	
		List<Device> list = new ArrayList<>();
		activeDevicesService.findAll().stream().forEach(d -> {
			Duration dif = Duration.between(d.getLastActivity(), LocalDateTime.now());
			if(dif.toMinutes() > 30)
				activeDevicesService.deleteByActiveDeviceId(d.getId());	
			else
				list.add(d.getDevice());
			});
		return list;
//				activeDevicesService.findAll().stream().map(ActiveDevice::getDevice).collect(Collectors.toList());
	}
	public List<Device> getAllDevices(){
		return devicesRepository.findAll();
	}
	public List<Device> getDevicesByType(TypeOfDevice type){
		return devicesRepository.findByType(type);
	}
	public List<Device> getDevicesAddedBefore(LocalDateTime to){
		return devicesRepository.findByAddedAtBefore(to);
	}
	public List<Device> getDevicesAddedAfter(LocalDateTime from){
		return devicesRepository.findByAddedAtAfter(from);
	}
	public List<Device> getDevicesAddedBetween(LocalDateTime from,LocalDateTime to){
		List<Device> listFrom = this.getDevicesAddedAfter(from);
		List<Device> listTo = this.getDevicesAddedBefore(to);
		listFrom.retainAll(listTo);
		return listFrom;
	}
	public List<Device> findAllWithPagination(Integer page,Integer devicesPerPage){
		return devicesRepository.findAll(PageRequest.of(page, devicesPerPage)).getContent();
	}
	
}
