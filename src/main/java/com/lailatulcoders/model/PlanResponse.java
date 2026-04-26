package com.lailatulcoders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanResponse {

    public String action;
    public String product;
    
    @JsonProperty("product_id")
    public Integer product_id;
    
    public String result;
}