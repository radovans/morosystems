package cz.sinko.morosystems.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for time series data from Frankfurter API.
 *
 * @author Radovan Šinko
 */
@Data
@Builder
public class TimeSeriesExchangeRateDto {

	private String base;

	private LocalDate startDate;

	private LocalDate endDate;

	private BigDecimal amount;

	private Map<LocalDate, Map<String, BigDecimal>> rates;
}
