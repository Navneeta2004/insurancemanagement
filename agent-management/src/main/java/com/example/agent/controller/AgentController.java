package com.example.agent.controller;

import com.example.agent.dto.*;
import com.example.agent.service.AgentService;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    @Autowired
    private AgentService service;

    @PostMapping
    public ResponseEntity<AgentDto> createAgent(@Valid @RequestBody AgentCreateRequest request) {
        AgentDto agent = service.createAgent(request);
        return new ResponseEntity<>(agent, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentDto> updateAgent(@PathVariable Long id, @RequestBody AgentUpdateRequest request) {
        AgentDto agent = service.updateAgent(id, request);
        return new ResponseEntity<>(agent,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentDto> getAgent(@PathVariable Long id) {
        AgentDto agent = service.getAgentById(id);
        return new ResponseEntity<>(agent,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AgentDto>> getAll() {
        List<AgentDto> agents = service.getAllAgents();
        return new ResponseEntity<>(agents,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteAgent(id);
        return new ResponseEntity<>(HttpStatus.GONE);

    }

    @GetMapping("/{id}/customers") 
    public ResponseEntity<List<String>> customers(@PathVariable Long id) {
        List<String> customers = service.getAgentCustomers(id);
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }
     
    @GetMapping("/{id}/commission")
    public ResponseEntity<Double> getAgentCommission(@PathVariable Long id) {
        return ResponseEntity.ok(service.calculateCommission(id));
    }

    @PostMapping("/{id}/policies")
    public ResponseEntity<AgentDto> assignPolicies(@PathVariable Long id, @RequestBody List<String> policies) {
        AgentDto agent = service.assignPolicies(id, policies);
        return new ResponseEntity<>(agent,HttpStatus.CREATED);
    }

    @GetMapping("/{id}/policies")
    public ResponseEntity<List<String>> getAssignedPolicies(@PathVariable Long id) {
        AgentDto agent = service.getAgentById(id);
        return new ResponseEntity<>(agent.getAssignedPolicies(), HttpStatus.OK);

    }
}
