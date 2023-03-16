package com.company.test.IotRestProject.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.test.IotRestProject.models.Device;
import com.company.test.IotRestProject.models.TypeOfDevice;
@Repository
public interface DevicesRepository extends JpaRepository<Device, Integer>{
	Optional<Device> findBySerialNumber(String serialNumber);
	List<Device> findByType(TypeOfDevice type);
	List<Device> findByAddedAtBefore(LocalDateTime to);
	List<Device> findByAddedAtAfter(LocalDateTime from);
	Optional<Device> findByName(String name);
}
