
package com.example.agent.controller;

import com.example.agent.dto.*;
import com.example.agent.service.AgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService service;

    @PostMapping
    public ResponseEntity<AgentDto> createAgent(@Valid @RequestBody AgentCreateRequest request) {
        return ResponseEntity.ok(service.createAgent(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentDto> updateAgent(@PathVariable Long id, @RequestBody AgentUpdateRequest request) {
        return ResponseEntity.ok(service.updateAgent(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentDto> getAgent(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAgentById(id));
    }

    @GetMapping
    public ResponseEntity<List<AgentDto>> getAll() {
        return ResponseEntity.ok(service.getAllAgents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteAgent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/align")
    public ResponseEntity<Void> align(@PathVariable Long id, @RequestParam String territory) {
        service.alignAgentToTerritory(id, territory);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/customers")
    public ResponseEntity<List<String>> customers(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAgentCustomers(id));
    }

    @GetMapping("/{id}/commission")
    public ResponseEntity<Double> commission(@PathVariable Long id) {
        return ResponseEntity.ok(service.calculateCommission(id));
    }
}
