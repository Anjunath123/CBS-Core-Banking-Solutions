package com.canfin.corebanking.customerservice.controller;

import com.canfin.corebanking.customerservice.dto.AiChatRequest;
import com.canfin.corebanking.customerservice.dto.AiChatResponse;
import com.canfin.corebanking.customerservice.dto.BaseResponse;
import com.canfin.corebanking.customerservice.dto.TtsRequest;
import com.canfin.corebanking.customerservice.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/openai")
public class OpenAiController {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiController.class);

    private final OpenAiService openAiService;

    public OpenAiController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping(value = "/chat", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> chat(@Valid @RequestBody AiChatRequest request) {
        logger.info("OpenAI chat request received");
        String reply = openAiService.chat(request.getMessage());

        BaseResponse<AiChatResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(new AiChatResponse(reply));
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("OpenAI response generated successfully");
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping(value = "/tts", consumes = "application/json")
    public ResponseEntity<byte[]> textToSpeech(@Valid @RequestBody TtsRequest request) {
        logger.info("TTS request received");
        byte[] audio = openAiService.textToSpeech(
                request.getInput(), request.getVoice(), request.getInstructions());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=speech.mp3");
        return new ResponseEntity<>(audio, headers, HttpStatus.OK);
    }
}
