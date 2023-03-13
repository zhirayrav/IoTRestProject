package com.company.test.IotRestProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.test.IotRestProject.models.Payload;
@Repository
public interface PayloadRepository extends JpaRepository<Payload, Integer>{

}
