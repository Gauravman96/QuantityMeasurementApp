package com.microservice.history_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.history_service.entity.History;
import com.microservice.history_service.repository.HistoryRepository;

import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository repository;

    // SAVE
    public History save(History history) {
        return repository.save(history);
    }

    // GET BY USER
    public List<History> getByUser(String username) {
        return repository.findByUsername(username);
    }

    // GET BY OPERATION
    public List<History> getByOperation(String operation) {
        return repository.findByOperation(operation);
    }

    // GET BY USER + OPERATION
    public List<History> getByUserAndOperation(String username, String operation) {
        return repository.findByUsernameAndOperation(username, operation);
    }

    // DELETE single
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // DELETE all
    public void deleteAll() {
        repository.deleteAll();
    }
    
    public void deleteByUser(String username) {
        repository.deleteByUsername(username);
    }
}