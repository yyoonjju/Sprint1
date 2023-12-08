package com.example.sprint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sprint.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service,String>  {

}
