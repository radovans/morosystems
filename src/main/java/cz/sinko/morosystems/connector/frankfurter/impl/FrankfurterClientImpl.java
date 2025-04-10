package cz.sinko.morosystems.connector.frankfurter.impl;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import cz.sinko.morosystems.connector.common.BadRequestType;
import cz.sinko.morosystems.connector.common.ServerErrorRequestType;
import cz.sinko.morosystems.connector.common.SystemApiClientException;
import cz.sinko.morosystems.connector.common.SystemApiServerException;
import cz.sinko.morosystems.connector.frankfurter.FrankfurterClient;
import cz.sinko.morosystems.connector.frankfurter.dto.ExchangeRatesResponse;
import cz.sinko.morosystems.connector.frankfurter.dto.TimeSeriesExchangeRatesResponse;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link FrankfurterClient}
 *
 * @author Radovan Šinko
 */
@Service
public class FrankfurterClientImpl implements FrankfurterClient {

    private static final String BASE = "base";
    private static final String SYMBOLS = "symbols";
    private static final String AMOUNT = "amount";
    private static final String CURRENCIES = "currencies";
    private static final String LATEST = "latest";

    private final WebClient webClient;

    /**
     * Constructor for FrankfurterClientImpl.
     *
     * @param webClient the web client
     */
    public FrankfurterClientImpl(@Qualifier("frankfurterWebClient") final WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public ExchangeRatesResponse getLatestRates(final String base, final String symbols, final BigDecimal amount) {
        final Map<String, String> queryParams = fillQueryParams(base, symbols, amount);

        return callGet(uriBuilder -> {
            uriBuilder.path("/" + LATEST);
            queryParams.forEach(uriBuilder::queryParam);
            return uriBuilder.build();
        }, ExchangeRatesResponse.class);
    }

    @Override
    public ExchangeRatesResponse getRatesForDate(final LocalDate date, final String base, final String symbols, final BigDecimal amount) {
        Assert.notNull(date, "date must not be null");

        final Map<String, String> queryParams = fillQueryParams(base, symbols, amount);
        return callGet(uriBuilder -> {
            uriBuilder.path("/" + date);
            queryParams.forEach(uriBuilder::queryParam);
            return uriBuilder.build();
        }, ExchangeRatesResponse.class);
    }

    @Override
    public TimeSeriesExchangeRatesResponse getTimeSeriesRates(final LocalDate startDate, final LocalDate endDate, final String base, final String symbols, final BigDecimal amount) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate must not be null");

        final Map<String, String> queryParams = fillQueryParams(base, symbols, amount);

        return callGet(uriBuilder -> {
            uriBuilder.path("/" + startDate + ".." + endDate);
            queryParams.forEach(uriBuilder::queryParam);
            return uriBuilder.build();
        }, TimeSeriesExchangeRatesResponse.class);
    }

    @Override
    public Map<String, String> getSupportedCurrencies() {
        return callGet(uriBuilder -> {
            uriBuilder.path("/" + CURRENCIES);
            return uriBuilder.build();
        }, new ParameterizedTypeReference<>() {
        });
    }

    private <T> T callGet(Function<UriBuilder, URI> uriBuilder, Class<T> responseType) {
        return callGet(uriBuilder, ParameterizedTypeReference.forType(responseType));
    }

    private <T> T callGet(Function<UriBuilder, URI> uriBuilder, ParameterizedTypeReference<T> responseType) {
        return Mono.defer(() -> webClient.get()
                        .uri(uriBuilder)
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::is4xxClientError,
                                e -> Mono.error(new SystemApiClientException(BadRequestType.INVALID_REQUEST, "Request failed")))
                        .onStatus(
                                HttpStatusCode::is5xxServerError,
                                e -> Mono.error(new SystemApiServerException(ServerErrorRequestType.SERVICE_UNAVAILABLE, "Remote service error")))
                        .bodyToMono(responseType))
                .contextCapture()
                .block();
    }

    private static Map<String, String> fillQueryParams(final String base, final String symbols, final BigDecimal amount) {
        final Map<String, String> queryParams = new HashMap<>();
        if (!StringUtils.isEmpty(base)) {
            queryParams.put(BASE, base);
        }
        if (!StringUtils.isEmpty(symbols)) {
            queryParams.put(SYMBOLS, symbols);
        }
        if (!Objects.isNull(amount)) {
            queryParams.put(AMOUNT, amount.toString());
        }
        return queryParams;
    }
}
