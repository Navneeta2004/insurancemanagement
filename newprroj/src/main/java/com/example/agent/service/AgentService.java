package com.example.agent.service;

import com.example.agent.model.Agent;
import com.example.agent.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Optional<Agent> getAgentById(Long id) {
        return agentRepository.findById(id);
    }

    public Agent createAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    public Agent updateAgent(Long id, Agent updatedAgent) {
        return agentRepository.findById(id).map(agent -> {
            agent.setName(updatedAgent.getName());
            agent.setEmail(updatedAgent.getEmail());
            agent.setPhone(updatedAgent.getPhone());
            agent.setAddress(updatedAgent.getAddress());
            return agentRepository.save(agent);
        }).orElseGet(() -> {
            updatedAgent.setId(id);
            return agentRepository.save(updatedAgent);
        });
    }

    public void deleteAgent(Long id) {
        agentRepository.deleteById(id);
    }
}
