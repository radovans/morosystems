package cz.sinko.morosystems.facade.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import cz.sinko.morosystems.facade.ExchangeFacade;
import cz.sinko.morosystems.service.dto.CurrenciesDto;
import cz.sinko.morosystems.service.dto.ExchangeRatesDto;
import cz.sinko.morosystems.service.dto.TimeSeriesExchangeRateDto;
import cz.sinko.morosystems.service.ExchangeService;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link ExchangeFacade}
 *
 * @author Radovan Šinko
 */
@Service
@RequiredArgsConstructor
public class ExchangeFacadeImpl implements ExchangeFacade {

    private final ExchangeService exchangeService;

    @Override
    public ExchangeRatesDto getLatestRates(final String base, final String symbols, final BigDecimal amount) {
        return exchangeService.getLatestRates(base, symbols, amount);
    }

    @Override
    public ExchangeRatesDto getRatesForDate(final LocalDate date, final String base, final String symbols, final BigDecimal amount) {
        return exchangeService.getRatesForDate(date, base, symbols, amount);
    }

    @Override
    public TimeSeriesExchangeRateDto getTimeSeriesRates(final LocalDate startDate, final LocalDate endDate, final String base, final String symbols, final BigDecimal amount) {
        return exchangeService.getTimeSeriesRates(startDate, endDate, base, symbols, amount);
    }

    @Override
    public CurrenciesDto getSupportedCurrencies() {
        return exchangeService.getSupportedCurrencies();
    }
}
