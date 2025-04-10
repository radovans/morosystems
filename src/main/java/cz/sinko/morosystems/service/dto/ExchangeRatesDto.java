package cz.sinko.morosystems.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for latest rate from Frankfurter API.
 *
 * @author Radovan Šinko
 */
@Data
@Builder
public class ExchangeRatesDto {

	private String base;

	private LocalDate date;

	private BigDecimal amount;

	private Map<String, BigDecimal> rates;
}
