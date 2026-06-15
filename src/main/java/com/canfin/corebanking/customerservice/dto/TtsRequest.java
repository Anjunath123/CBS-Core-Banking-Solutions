package com.canfin.corebanking.customerservice.dto;

import javax.validation.constraints.NotBlank;

public class TtsRequest {

    @NotBlank(message = "Input text is required")
    private String input;

    private String voice = "alloy";

    private String instructions;

    public TtsRequest() {}

    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }

    public String getVoice() { return voice; }
    public void setVoice(String voice) { this.voice = voice; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}
