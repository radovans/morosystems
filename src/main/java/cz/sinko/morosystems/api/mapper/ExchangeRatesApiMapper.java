package cz.sinko.morosystems.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import cz.sinko.morosystems.api.dto.response.exchangerate.ExchangeRateResponse;
import cz.sinko.morosystems.api.dto.response.exchangerate.SupportedCurrenciesResponse;
import cz.sinko.morosystems.api.dto.response.exchangerate.TimeSeriesExchangeRatesResponse;
import cz.sinko.morosystems.service.dto.CurrenciesDto;
import cz.sinko.morosystems.service.dto.ExchangeRatesDto;
import cz.sinko.morosystems.service.dto.TimeSeriesExchangeRateDto;


/**
 * Mapper between User and Request/Response UserDto.
 *
 * @author Radovan Šinko
 */
@Mapper(componentModel = "spring")
public interface ExchangeRatesApiMapper {

    /**
     * Get instance of UserMapper.
     *
     * @return instance of UserMapper
     */
    static ExchangeRatesApiMapper t() {
        return Mappers.getMapper(ExchangeRatesApiMapper.class);
    }

    /**
     * Map ExchangeRatesDto to ExchangeRateResponse.
     *
     * @param source ExchangeRatesDto
     * @return ExchangeRateResponse
     */
    ExchangeRateResponse toResponse(ExchangeRatesDto source);

    /**
     * Map TimeSeriesExchangeRateDto to TimeSeriesExchangeRatesResponse.
     *
     * @param source TimeSeriesExchangeRateDto
     * @return TimeSeriesExchangeRatesResponse
     */
    TimeSeriesExchangeRatesResponse toResponse(TimeSeriesExchangeRateDto source);

    /**
     * Map CurrenciesDto to SupportedCurrenciesResponse.
     *
     * @param source CurrenciesDto
     * @return SupportedCurrenciesResponse
     */
    SupportedCurrenciesResponse toResponse(CurrenciesDto source);
}
