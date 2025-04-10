package cz.sinko.morosystems.api.dto.response.exchangerate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * Response object for User.
 *
 * @author Radovan Šinko
 */
@Data
@Builder
public class TimeSeriesExchangeRatesResponse {

    private String base;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal amount;
    private Map<LocalDate, Map<String, BigDecimal>> rates;
}
