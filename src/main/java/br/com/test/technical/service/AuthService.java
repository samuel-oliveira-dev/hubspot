package br.com.test.technical.service;

import br.com.test.technical.context.GlobalContext;
import br.com.test.technical.dto.TokenResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import javax.naming.AuthenticationException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.logging.Logger;

@Service
public class AuthService {
    private final GlobalContext globalContext;
    Logger logger = Logger.getLogger(AuthService.class.getName());

    @Value("${hubspot.client-id}")
    private String clientId;
    @Value("${hubspot.client-secret}")
    private String clientSecret;
    @Value("${hubspot.redirect-uri}")
    private String redirectUri;

    private final WebClient hubspotWebClient;

    public AuthService(WebClient hubspotWebClient, GlobalContext globalContext) {
        this.hubspotWebClient = hubspotWebClient;
        this.globalContext = globalContext;
    }



    public String buildAuthUrl(){
        return UriComponentsBuilder
                .fromUriString("https://app.hubspot.com/oauth/authorize")
                .queryParam("client_id",  this.clientId)
                .queryParam("scope", URLEncoder.encode("crm.objects.contacts.read crm.objects.contacts.write", StandardCharsets.UTF_8))
                .queryParam("redirect_uri", this.redirectUri)
                .build()
                .toUriString();
    }


    public TokenResponseDTO requestToken(String code){
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("client_id", this.clientId);
        body.add("client_secret", this.clientSecret);
        body.add("redirect_uri", this.redirectUri);

        TokenResponseDTO token = null;
        try {
            token =  hubspotWebClient.post()
                    .uri("/oauth/v1/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(TokenResponseDTO.class)
                    .block();
            saveToken(token);
        } catch (Exception ex){
            logger.severe(ex.getMessage());
            return null;
        }

        return token;
    }

    private static final String CONTEXT_KEY = "hubspot_token";

    public void saveToken(TokenResponseDTO token){
        token.setAcquiredAt(Instant.now());
        globalContext.put(CONTEXT_KEY, token);
    }


    public TokenResponseDTO getToken(String code){
        TokenResponseDTO token = (TokenResponseDTO) globalContext.get(CONTEXT_KEY);
        if(token == null){
            token = requestToken(code);
            return  token;
        }

        if(token.isExpired()){
            token = refreshToken(token);
            return token;
        }
        return null;
    }

    public TokenResponseDTO refreshToken(TokenResponseDTO token){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("refresh_token", token.getRefreshToken());

        token = hubspotWebClient.post()
                .uri("https://api.hubapi.com/oauth/v1/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(TokenResponseDTO.class)
                .block();
        saveToken(token);
        return token;
    }

    public TokenResponseDTO getToken() throws AuthenticationException {
        TokenResponseDTO token = (TokenResponseDTO) globalContext.get(CONTEXT_KEY);
        if(token != null && !token.isExpired()){
            return token;
        } else {
            try {
                token = refreshToken(token);
            } catch (Exception ex){
                throw new AuthenticationException("Failed to refresh access token. Please authenticate again. Details: " + ex.getMessage());
            }
        }
        return token;
    }
}
