package com.canfin.corebanking.customerservice.service;

public interface OpenAiService {
    String chat(String userMessage);
    byte[] textToSpeech(String input, String voice, String instructions);
}
