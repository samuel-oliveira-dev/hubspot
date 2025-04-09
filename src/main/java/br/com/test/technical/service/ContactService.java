package br.com.test.technical.service;

import br.com.test.technical.dto.ContactRequestDTO;
import br.com.test.technical.dto.ContactResponseDTO;
import br.com.test.technical.dto.TokenResponseDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContactService {
    private final WebClient hubspotWebClient;
    private final AuthService authService;

    public ContactService(WebClient hubspotWebClient,AuthService authService) {
        this.hubspotWebClient = hubspotWebClient;
        this.authService = authService;
    }

    public ContactResponseDTO createContact(ContactRequestDTO contact) throws AuthenticationException {
       Map<String, Object> body = Map.of(
               "properties", Map.of(
                       "firstname", contact.getFirstName(),
                       "lastname", contact.getLastName(),
                       "email", contact.getEmail()
               )
       );
       TokenResponseDTO token = authService.getToken();
       return hubspotWebClient.post()
               .uri("/crm/v3/objects/contacts")
               .contentType(MediaType.APPLICATION_JSON)
               .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken())
               .bodyValue(body)
               .retrieve()
               .bodyToMono(ContactResponseDTO.class)
               .block();

    }
}
