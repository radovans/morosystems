package cz.sinko.morosystems.api.dto.response.exchangerate;

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
public class SupportedCurrenciesResponse {

    private Map<String, String> currencies;
}
