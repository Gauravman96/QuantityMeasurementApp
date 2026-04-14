package com.microservice.history_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.history_service.entity.History;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByUsername(String username);

    List<History> findByOperation(String operation);

    List<History> findByUsernameAndOperation(String username, String operation);
    @Transactional
    @Modifying
    void deleteByUsername(String username);
}