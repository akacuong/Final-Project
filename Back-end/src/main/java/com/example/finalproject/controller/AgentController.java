package com.example.finalproject.controller;

import com.example.finalproject.dto.AgentDTO;
import com.example.finalproject.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    // ✅ Get all agents
    @GetMapping
    public ResponseEntity<List<AgentDTO>> getAllAgents() {
        return ResponseEntity.ok(agentService.getAllAgents());
    }

    // ✅ Get agent by ID (Fixed: Integer instead of Long)
    @GetMapping("/{id}")
    public ResponseEntity<AgentDTO> getAgentById(@PathVariable Integer id) {
        return ResponseEntity.ok(agentService.getAgentById(id));
    }

    // ✅ Create agent
    @PostMapping
    public ResponseEntity<AgentDTO> createAgent(@RequestBody AgentDTO agentDTO) {
        return ResponseEntity.status(201).body(agentService.createAgent(agentDTO));
    }

    // ✅ Update agent (Fixed: Integer instead of Long)
    @PutMapping("/{id}")
    public ResponseEntity<AgentDTO> updateAgent(@PathVariable Integer id, @RequestBody AgentDTO agentDTO) {
        return ResponseEntity.ok(agentService.updateAgent(id, agentDTO));
    }

    // ✅ Delete agent (Fixed: Integer instead of Long)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Integer id) {
        agentService.deleteAgent(id);
        return ResponseEntity.noContent().build();
    }
}
