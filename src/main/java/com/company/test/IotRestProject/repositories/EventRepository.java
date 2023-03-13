package com.company.test.IotRestProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.test.IotRestProject.models.Event;
@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{

}
