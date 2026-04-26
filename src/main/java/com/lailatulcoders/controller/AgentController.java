package com.lailatulcoders.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lailatulcoders.model.AgentResponse;
import com.lailatulcoders.service.AgentLoopService;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/agent")
public class AgentController {
    
    private final AgentLoopService agentLoopService;

    public AgentController(AgentLoopService agentLoopService) {
        this.agentLoopService = agentLoopService;
    }

    @PostMapping("/chat")
    public AgentResponse chat(@RequestBody Map<String, String> request) {
        System.out.println("test controller debug");
        
        String message = request.get("message");
        System.out.println("MESSAGE = " + message);
        return agentLoopService.run(message);
    }
    
}
