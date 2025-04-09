package br.com.test.technical.configuration;

import br.com.test.technical.components.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfiguration {
    @Bean
    public WebClient hubSpotWebClient(WebClient.Builder builder, RateLimiter rateLimiter) {
        return builder
                .baseUrl("https://api.hubapi.com")
                .filter(ExchangeFilterFunction.ofRequestProcessor(request->{
                    rateLimiter.acquire();
                    return Mono.just(request);
                }))
                .build();
    }
}
