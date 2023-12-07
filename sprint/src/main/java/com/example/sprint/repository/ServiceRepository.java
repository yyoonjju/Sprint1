package com.example.sprint.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sprint.model.Service;

public interface ServiceRepository extends JpaRepository<Service,String>  {

}
