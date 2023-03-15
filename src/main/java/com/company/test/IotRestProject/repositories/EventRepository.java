package com.company.test.IotRestProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.test.IotRestProject.models.Device;
import com.company.test.IotRestProject.models.Event;
@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
	List<Event> findByDevice(Device device);
}
