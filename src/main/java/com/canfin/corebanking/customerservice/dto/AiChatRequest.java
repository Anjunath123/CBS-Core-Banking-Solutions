package com.canfin.corebanking.customerservice.dto;

import javax.validation.constraints.NotBlank;

public class AiChatRequest {

    @NotBlank(message = "Message is required")
    private String message;

    public AiChatRequest() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
