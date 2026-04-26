package com.lailatulcoders.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.lailatulcoders.model.AgentState;
import com.lailatulcoders.model.PlanResponse;

@Service
public class GeminiService {

    private final Client client;
    private final ObjectMapper objectMapper;

    public GeminiService(Client client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public String ask(String message) {
            System.out.println("ask method entered");
            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.5-flash",
                            message,
                            null
                    );

            System.out.println("raw full: " + response);
            System.out.println("text: " + response.text());
            return response.text();
    }

    public PlanResponse plan(AgentState state) {

        try {
            System.out.println("entered gemini plan");

            String prompt = """
                You are an AI tool router.

                Return ONLY valid JSON. No markdown. No extra text.

                You MUST choose ONE action:

                ACTIONS:
                - find_product (input: product)
                - get_suppliers (input: product_id)
                - check_inventory (input: product_id)
                - final_answer (input: result)

                CURRENT STATE:
                %s

                OUTPUT FORMAT:
                {
                "action": "",
                "product": "",
                "product_id": 0,
                "result": ""
                }
            """ + state;

            String raw = ask("hello");
            System.out.println("raw response: " + raw);

            String cleaned = extractJson(raw);
            System.out.println("cleaned json: "+ cleaned);
            JsonNode node = objectMapper.readTree(cleaned);
            System.out.println("parsed result field: " + node.get("result"));

            if (cleaned == null) {
                return fallback("invalid_json");
            }

            PlanResponse parsed = objectMapper.readValue(cleaned, PlanResponse.class);
            System.out.println("parsed action: " + parsed.action);
            System.out.println("parsed result: " + parsed.result);
            System.out.println("parsed productid: " + parsed.product_id);

            if (parsed.action == null) {
                return fallback("missing_action");
            }

            return parsed;

        } catch (Exception e) {
            return fallback(e.getMessage());
        }
    }

    private String extractJson(String raw) {
        if (raw == null) return fallbackJson("NULL_RAW");

        raw = raw.trim()
                .replace("```json", "")
                .replace("```", "")
                .trim();

        int start = raw.indexOf('{');
        int end = raw.lastIndexOf('}');

        if (start >= 0 && end > start) {
            return raw.substring(start, end + 1);
        }
        System.out.println("NO JSON FOUND RAW = " + raw);

        return fallbackJson(raw);
    }

    private String fallbackJson(String raw) {
        return """
        {
        "action": "final_answer",
        "product": null,
        "product_id": null,
        "result": "LLM returned non-JSON: %s"
        }
        """.formatted(raw.replace("\"", "'"));
    }

    private PlanResponse fallback(String msg) {
        PlanResponse p = new PlanResponse();
        p.action = "final_answer";
        p.result = "Agent error: " + msg;
        return p;
    }
}