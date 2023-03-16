package com.company.test.IotRestProject.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.company.test.IotRestProject.dto.EventCountGroupByDeviceTypeDTO;
import com.company.test.IotRestProject.models.Device;
import com.company.test.IotRestProject.models.Event;
@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
	List<Event> findByDevice(Device device);
	List<Event> findByDevice(Device device,Pageable page);
	@Query("select e from Event e join fetch e.device d where d.serialNumber=:serialNumber and e.happenedAt<:to")
	List<Event> findByDeviseSerialNumberHappenedAtBefore(String serialNumber,LocalDateTime to);
	@Query("select e from Event e join fetch e.device d where d.serialNumber=:serialNumber and e.happenedAt>:from")
	List<Event> findByDeviseSerialNumberHappenedAtAfter(String serialNumber,LocalDateTime from);
	@Query("select e from Event e join fetch e.device d where d.serialNumber=:serialNumber and e.happenedAt>:from and e.happenedAt<:to")
	List<Event> findByDeviseSerialNumberHappenedAtBetween(String serialNumber,LocalDateTime from,LocalDateTime to);
	
	@Query("select new com.company.test.IotRestProject.dto.EventCountGroupByDeviceTypeDTO(d.type,count(e)) from Event e join e.device d where e.happenedAt>:from and e.happenedAt<:to group by d.type")
	List<EventCountGroupByDeviceTypeDTO> findCountGroupByDeviceTypeBetween(LocalDateTime from,LocalDateTime to);
	@Query("select new com.company.test.IotRestProject.dto.EventCountGroupByDeviceTypeDTO(d.type,count(e)) from Event e join e.device d where e.happenedAt<:to group by d.type")
	List<EventCountGroupByDeviceTypeDTO> findCountGroupByDeviceTypeBefore(LocalDateTime to);
	@Query("select new com.company.test.IotRestProject.dto.EventCountGroupByDeviceTypeDTO(d.type,count(e)) from Event e join e.device d where e.happenedAt>:from group by d.type")
	List<EventCountGroupByDeviceTypeDTO> findCountGroupByDeviceTypeAfter(LocalDateTime from);
	@Query("select new com.company.test.IotRestProject.dto.EventCountGroupByDeviceTypeDTO(d.type,count(e)) from Event e join e.device d group by d.type")
	List<EventCountGroupByDeviceTypeDTO> findCountGroupByDeviceType();
	
}
