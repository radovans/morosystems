package cz.sinko.morosystems.connector.frankfurter;

import static net.logstash.logback.marker.Markers.append;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * WebClient configuration.
 *
 * @author Radovan Šinko
 */
@Configuration
@Slf4j
public class WebClientConfiguration {

    /**
     * Creates a WebClient bean with logging filters.
     *
     * @param builder the WebClient builder
     * @return the configured WebClient
     */
    @Bean("frankfurterWebClient")
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .filter(logRequest())
                .filter(logResponse())
                .baseUrl("https://api.frankfurter.dev/v1")
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            final WebClientRequest request = createWebClientRequest(clientRequest);
            if (log.isInfoEnabled()) {
                log.info(append("client-request", request), "WebClient Request: {} {}", clientRequest.method(), clientRequest.url());
            }
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            final WebClientResponse response = createWebClientResponse(clientResponse);
            if (log.isInfoEnabled()) {
                log.info(append("client-response", response), "WebClient Response: {}", clientResponse.statusCode());
            }
            return Mono.just(clientResponse);
        });
    }

    private WebClientRequest createWebClientRequest(final ClientRequest clientRequest) {
        return WebClientRequest.builder()
                .method(clientRequest.method().name())
                .url(clientRequest.url().toString())
                .headers(clientRequest.headers().toSingleValueMap())
                .attributes(clientRequest.attributes())
                .body(clientRequest.body().toString())
                .build();
    }

    private WebClientResponse createWebClientResponse(final ClientResponse clientResponse) {
        return WebClientResponse.builder()
                .method(clientResponse.request().getMethod().name())
                .url(clientResponse.request().getURI().toString())
                .statusCode(clientResponse.statusCode().value())
                .headers(clientResponse.headers().asHttpHeaders().toSingleValueMap())
                .body(clientResponse.bodyToMono(String.class))
                .build();
    }

    @Data
    @Builder
    public static class WebClientRequest {
        private String url;

        private String method;

        private Map<String, String> headers;

        private Map<String, Object> attributes;

        private String body;
    }

    @Data
    @Builder
    public static class WebClientResponse {
        private String url;

        private String method;

        private int statusCode;

        private Map<String, String> headers;

        private Mono<String> body;
    }
}
