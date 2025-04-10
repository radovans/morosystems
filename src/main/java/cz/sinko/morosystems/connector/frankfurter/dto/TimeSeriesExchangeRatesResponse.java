package cz.sinko.morosystems.connector.frankfurter.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO for time series data from Frankfurter API.
 *
 * @author Radovan Šinko
 */
@Data
public class TimeSeriesExchangeRatesResponse {

	private String base;

	@JsonProperty("start_date")
	private LocalDate startDate;

	@JsonProperty("end_date")
	private LocalDate endDate;

	private BigDecimal amount;

	private Map<LocalDate, Map<String, BigDecimal>> rates;
}
