
package com.example.agent.service.impl;

import com.example.agent.dto.*;
import com.example.agent.entity.Agent;
import com.example.agent.repository.AgentRepository;
import com.example.agent.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository repository;

    @Override
    public AgentDto createAgent(AgentCreateRequest request) {
        Agent agent = Agent.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .phone(request.getPhone())
            .status(request.getStatus())
            .build();
        return toDto(repository.save(agent));
    }

    @Override
    public AgentDto updateAgent(Long id, AgentUpdateRequest request) {
        Agent agent = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        agent.setPhone(request.getPhone());
        agent.setStatus(request.getStatus());
        return toDto(repository.save(agent));
    }

    @Override
    public AgentDto getAgentById(Long id) {
        return toDto(repository.findById(id).orElseThrow(() -> new RuntimeException("Not found")));
    }

    @Override
    public List<AgentDto> getAllAgents() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAgent(Long id) {
        repository.deleteById(id);
    }

    @Override
    public AgentDto assignPolicies(Long id, List<String> policies) {
        return getAgentById(id);
    }

    @Override
    public void alignAgentToTerritory(Long id, String territoryCode) {
        Agent agent = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        agent.setTerritoryCode(territoryCode);
        repository.save(agent);
    }

    @Override
    public List<String> getAgentCustomers(Long id) {
        return List.of("Customer A", "Customer B");
    }

    @Override
    public double calculateCommission(Long id) {
        return 500.0;
    }

    private AgentDto toDto(Agent agent) {
        return AgentDto.builder()
                .id(agent.getId())
                .firstName(agent.getFirstName())
                .lastName(agent.getLastName())
                .email(agent.getEmail())
                .phone(agent.getPhone())
                .status(agent.getStatus())
                .territoryCode(agent.getTerritoryCode())
                .build();
    }
}
