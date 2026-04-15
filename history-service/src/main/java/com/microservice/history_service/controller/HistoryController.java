package com.microservice.history_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.microservice.history_service.entity.History;
import com.microservice.history_service.service.HistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
public class HistoryController {

    @Autowired
    private HistoryService service;

    // SAVE (called by quantity-service)
    @PostMapping
    public History save(@RequestBody History history) {
        return service.save(history);
    }
 //  GET logged-in user history
    @GetMapping("/me")
    public List<History> getMyHistory(
            @RequestHeader("X-Username") String username) {

        return service.getByUser(username);
    }

    @GetMapping("/me/{operation}")
    public List<History> getMyHistoryByOperation(
            @RequestHeader("X-Username") String username,
            @PathVariable String operation) {

        return service.getByUserAndOperation(username, operation);
    }
    // GET BY USER
    @GetMapping("/{username}")
    public List<History> getByUser(@PathVariable String username) {
        return service.getByUser(username);
    }

    // GET BY OPERATION
    @GetMapping("/operation/{operation}")
    public List<History> getByOperation(@PathVariable String operation) {
        return service.getByOperation(operation);
    }

    // GET BY USER + OPERATION
    @GetMapping("/{username}/{operation}")
    public List<History> getByUserAndOperation(
            @PathVariable String username,
            @PathVariable String operation) {

        return service.getByUserAndOperation(username, operation);
    }
    
 // DELETE single record
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return "Deleted successfully";
    }

    // DELETE all records
    @DeleteMapping("/all")
    public String deleteMyHistory(
            @RequestHeader("X-Username") String username) {

        service.deleteByUser(username);
        return "User history deleted";
    }
}