package com.example.AIAgent.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.AIAgent.Service.OpenSerive;

@RestController
public class OpenAIController {
    
    @Autowired
    private  OpenSerive openAIService;

    @GetMapping("/ask")
    public ResponseEntity<String> ask(@RequestParam String question) {
        try {
            String answer = openAIService.askOpenAI(question);
            return ResponseEntity.ok(answer);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error: " + e.getMessage());
        }
    }
}
