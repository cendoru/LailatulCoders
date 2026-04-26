package com.lailatulcoders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AgentState {
    public String userInput;
    public String goal;
    public String currentAction;
    public Integer productId;
    public String productName;
    public Map<String, Object> lastResult;
    public List<String> history = new ArrayList<>();
}
