package br.com.test.technical.controller;

import br.com.test.technical.dto.ContactRequestDTO;
import br.com.test.technical.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;
    private final Logger logger = Logger.getLogger(ContactController.class.getName());
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody ContactRequestDTO contact) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(contactService.createContact(contact));
        } catch (Exception ex){
            logger.severe(ex.getCause().getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating contact");
        }
    }


}
