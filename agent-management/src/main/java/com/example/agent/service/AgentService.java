package com.example.agent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.agent.dto.AgentCreateRequest;
import com.example.agent.dto.AgentDto;
import com.example.agent.dto.AgentUpdateRequest;
import com.example.agent.entity.Agent;
import com.example.agent.exception.AgentNotFoundException;
import com.example.agent.repository.AgentRepository;

@Service
public class AgentService {
	
    @Autowired
    private AgentRepository repository;
    
    @Autowired
    private CommissionService commissionService;

    public AgentDto createAgent(AgentCreateRequest request) {
        Agent agent = new Agent();
        agent.setFirstName(request.getFirstName());
        agent.setLastName(request.getLastName());
        agent.setEmail(request.getEmail());
        agent.setPhone(request.getPhone());

        return AgentDto.fromEntity(repository.save(agent));
    }
    public AgentDto updateAgent(Long id, AgentUpdateRequest request) {
        Agent agent = repository.findById(id).orElseThrow(() -> new AgentNotFoundException("Agent not found"));
        agent.setPhone(request.getPhone());
        agent.setStatus(request.getStatus());

        return AgentDto.fromEntity(repository.save(agent));
    }

    public AgentDto getAgentById(Long id) {
        Agent agent = repository.findById(id).orElseThrow(() -> new AgentNotFoundException("Agent not found"));
        return AgentDto.fromEntity(agent);
    }


    public List<AgentDto> getAllAgents() {
        return repository.findAll().stream().map(AgentDto::fromEntity).toList();
    }

    public void deleteAgent(Long id) {
        repository.deleteById(id);
    }
    public List<String> getAgentCustomers(Long id) {
        return List.of("Customer A", "Customer B");
    }


    public double calculateCommission(Long id) {
        Agent agent = repository.findById(id).orElseThrow(() -> new AgentNotFoundException("Agent not found"));
        return commissionService.calculateCommission(agent.getAssignedPolicies().size());
    }
    public AgentDto assignPolicies(Long id, List<String> policies) {
        Agent agent = repository.findById(id).orElseThrow(() -> new AgentNotFoundException("Agent not found"));
        if (agent.getAssignedPolicies() == null) {
            agent.setAssignedPolicies(new ArrayList<>());
        }
        agent.getAssignedPolicies().addAll(policies);

        return AgentDto.fromEntity(repository.save(agent));
    }

   
}
