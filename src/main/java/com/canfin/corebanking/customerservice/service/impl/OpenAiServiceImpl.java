package com.canfin.corebanking.customerservice.service.impl;

import com.canfin.corebanking.customerservice.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import org.springframework.http.HttpMethod;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiServiceImpl.class);

    private static final String SYSTEM_PROMPT = "You are a helpful banking assistant for a core banking application. "
            + "Help users with questions about deposits, savings accounts, loans, fixed deposits, and general banking operations. "
            + "Provide concise and accurate responses.";

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String model;
    private final String apiKey;

    public OpenAiServiceImpl(@Qualifier("openAiRestTemplate") RestTemplate restTemplate,
                             @Value("${openai.api.url}") String apiUrl,
                             @Value("${openai.model}") String model,
                             @Value("${openai.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.model = model;
        this.apiKey = apiKey;
    }

    @Override
    public String chat(String userMessage) {
        logger.info("Sending message to OpenAI model: {}", model);

        Map<String, Object> request = new HashMap<>();
        request.put("model", model);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(createMessage("system", SYSTEM_PROMPT));
        messages.add(createMessage("user", userMessage));
        request.put("messages", messages);
        request.put("max_tokens", 1024);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl + "/chat/completions", httpEntity, Map.class);

        return extractContent(response.getBody());
    }

    private Map<String, String> createMessage(String role, String content) {
        Map<String, String> message = new HashMap<>();
        message.put("role", role);
        message.put("content", content);
        return message;
    }

    @Override
    public byte[] textToSpeech(String input, String voice, String instructions) {
        logger.info("Sending TTS request to RapidAPI");

        Map<String, Object> body = new HashMap<>();
        body.put("model", "tts-1");
        body.put("input", input);
        body.put("voice", voice != null ? voice : "alloy");
        if (instructions != null && !instructions.isEmpty()) {
            body.put("instructions", instructions);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-rapidapi-key", apiKey);
        headers.set("x-rapidapi-host", "open-ai-text-to-speech1.p.rapidapi.com");

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, byte[].class);

        return response.getBody();
    }

    @SuppressWarnings("unchecked")
    private String extractContent(Map responseBody) {
        if (responseBody == null) {
            return "No response from OpenAI";
        }
        List<Map> choices = (List<Map>) responseBody.get("choices");
        if (choices == null || choices.isEmpty()) {
            return "No response from OpenAI";
        }
        Map message = (Map) choices.get(0).get("message");
        return (String) message.get("content");
    }
}
