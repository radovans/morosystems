package cz.sinko.morosystems.api.controller;

import static cz.sinko.morosystems.api.ApiUris.ROOT_URI_RATES;
import static net.logstash.logback.marker.Markers.append;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.morosystems.api.mapper.ExchangeRatesApiMapper;
import cz.sinko.morosystems.api.dto.response.exchangerate.ExchangeRateResponse;
import cz.sinko.morosystems.api.dto.response.exchangerate.SupportedCurrenciesResponse;
import cz.sinko.morosystems.api.dto.response.exchangerate.TimeSeriesExchangeRatesResponse;
import cz.sinko.morosystems.facade.ExchangeFacade;
import cz.sinko.morosystems.repository.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for getting exchange rates.
 *
 * @author Sinko Radovan
 */
@RestController
@RequestMapping(ROOT_URI_RATES)
@RequiredArgsConstructor
@Slf4j
public class RatesController {

    private final ExchangeFacade exchangeFacade;

    private final ExchangeRatesApiMapper exchangeRatesApiMapper;

    /**
     * Get latest exchange rates.
     *
     * @param loggedUser logged user
     * @param base base currency
     * @param symbols target currencies
     * @param amount amount to convert
     * @return latest exchange rates
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/latest")
    public ResponseEntity<ExchangeRateResponse> getLatestRates(@AuthenticationPrincipal final User loggedUser, @RequestParam(required = false) final String base, @RequestParam(required = false) final String symbols, @RequestParam(required = false) final BigDecimal amount) {
        log.info("Call getLatestRates with base '{}', symbols '{}' and amount '{}'", base, symbols, amount);
        return ResponseEntity.ok(exchangeRatesApiMapper.toResponse(exchangeFacade.getLatestRates(base, symbols, amount)));
    }

    /**
     * Get exchange rates for a specific date.
     *
     * @param loggedUser logged user
     * @param date date to get exchange rates for
     * @param base base currency
     * @param symbols target currencies
     * @param amount amount to convert
     * @return exchange rates for the specified date
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/historical")
    public ResponseEntity<ExchangeRateResponse> getRatesForDate(@AuthenticationPrincipal final User loggedUser, @RequestParam final LocalDate date, @RequestParam(required = false) final String base, @RequestParam(required = false) final String symbols, @RequestParam(required = false) final BigDecimal amount) {
        log.info("Call getRatesForDate with date '{}', base '{}', symbols '{}' and amount '{}'", date, base, symbols, amount);
        return ResponseEntity.ok(exchangeRatesApiMapper.toResponse(exchangeFacade.getRatesForDate(date, base, symbols, amount)));
    }

    /**
     * Get exchange rates for a time series.
     *
     * @param loggedUser logged user
     * @param startDate start date
     * @param endDate end date
     * @param base base currency
     * @param symbols target currencies
     * @param amount amount to convert
     * @return exchange rates for the specified time series
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/timeseries")
    public ResponseEntity<TimeSeriesExchangeRatesResponse> getTimeSeriesRates(@AuthenticationPrincipal final User loggedUser, @RequestParam final LocalDate startDate, @RequestParam final LocalDate endDate, @RequestParam(required = false) final String base, @RequestParam(required = false) final String symbols, @RequestParam(required = false) final BigDecimal amount) {
        log.info("Call getTimeSeriesRates with startDate '{}', endDate '{}', base '{}', symbols '{}' and amount '{}'", startDate, endDate, base, symbols, amount);
        return ResponseEntity.ok(exchangeRatesApiMapper.toResponse(exchangeFacade.getTimeSeriesRates(startDate, endDate, base, symbols, amount)));
    }

    /**
     * Get supported currencies.
     *
     * @param loggedUser logged user
     * @return supported currencies
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/currencies")
    public ResponseEntity<SupportedCurrenciesResponse> getSupportedCurrencies(@AuthenticationPrincipal final User loggedUser) {
        log.info("Call getSupportedCurrencies");
        return ResponseEntity.ok(exchangeRatesApiMapper.toResponse(exchangeFacade.getSupportedCurrencies()));
    }
}
