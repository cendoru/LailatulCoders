package com.lailatulcoders.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lailatulcoders.model.AgentResponse;
import com.lailatulcoders.model.AgentState;
import com.lailatulcoders.model.PlanResponse;

@Service
public class AgentLoopService {

    private final GeminiService geminiService;
    private final ToolService toolService;

    public AgentLoopService(GeminiService geminiService, ToolService toolService) {
        this.geminiService = geminiService;
        this.toolService = toolService;
    }

    public AgentResponse run(String userMessage) {

        AgentState state = new AgentState();
        state.userInput = userMessage;
        state.goal = "Find best supplier for product requested by user";

        AgentResponse response = new AgentResponse();
        response.query = userMessage;

        for (int i = 0; i < 3; i++) {

            PlanResponse decision = geminiService.plan(state);

            if (decision == null || decision.action == null) {
                return error(response, state, "null_decision");
            }

            String action = decision.action;
            state.history.add(action);

            if ("final_answer".equals(action)) {

                response.action = action;
                response.status = "success";
                response.trace = state.history;

                response.result = decision.result;
                response.answer = decision.result != null
                        ? decision.result
                        : "No result";

                return response;
            }

            Map<String, Object> result =
                    toolService.execute(action, decision, state);

            state.currentAction = action;
            state.lastResult = result;

            if (result != null) {

                if (result.get("product_id") instanceof Number n) {
                    state.productId = n.intValue();
                }

                if (result.get("product_name") instanceof String s) {
                    state.productName = s;
                }

                state.history.add(result.toString());
            }
        }

        return error(response, state, "max_iterations_reached");
    }

    private AgentResponse error(AgentResponse response, AgentState state, String msg) {
        response.status = "failed";
        response.answer = "Agent error: " + msg;
        response.trace = state.history;
        response.action = state.currentAction;
        response.result = state.lastResult;
        return response;
    }
}