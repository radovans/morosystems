package cz.sinko.morosystems.connector.frankfurter.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import lombok.Data;

/**
 * DTO for exchange rates for single date from Frankfurter API.
 *
 * @author Radovan Šinko
 */
@Data
public class ExchangeRatesResponse {

	private String base;

	private LocalDate date;

	private BigDecimal amount;

	private Map<String, BigDecimal> rates;
}
