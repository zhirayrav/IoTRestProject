package com.company.test.IotRestProject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.test.IotRestProject.models.Device;
@Repository
public interface DevicesRepository extends JpaRepository<Device, Integer>{
	public Optional<Device> findBySerialNumber(String serialNumber);
}
