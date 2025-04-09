package br.com.test.technical.controller;

import br.com.test.technical.dto.WebhookEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/webhook")
@RestController
public class WebhookController {
    @PostMapping("/hubspot/recieve")
    public ResponseEntity<Void> recieveWebhook(@RequestBody List<WebhookEvent> events) {
        for(WebhookEvent event : events) {
            System.out.println(event.toString());
        }
        return ResponseEntity.ok().build();
    }
}
