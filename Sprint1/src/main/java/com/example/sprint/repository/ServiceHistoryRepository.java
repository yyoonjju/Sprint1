package com.example.sprint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sprint.model.ServiceHistory;
import java.util.List;

@Repository
public interface ServiceHistoryRepository extends JpaRepository<ServiceHistory, Long> {
    List<ServiceHistory> findByUserId(String userId);

    List<ServiceHistory> findAllByOrderBySubStartDesc();
}
