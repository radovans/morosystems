package cz.sinko.morosystems.service.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for supported currencies from Frankfurter API.
 *
 * @author Radovan Šinko
 */
@Data
@Builder
public class CurrenciesDto {

    private Map<String, String> currencies;
}
