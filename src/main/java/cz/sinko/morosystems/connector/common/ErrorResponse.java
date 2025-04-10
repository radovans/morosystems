package cz.sinko.morosystems.connector.common;

import lombok.Data;

/**
 * Common error response from Money Manager to consumer.
 *
 * @author Radovan Šinko
 */
@Data
public class ErrorResponse {

    /**
     * Error code. Simple problem identification.
     */
    private final String errorCode;

    /**
     * Detailed description of problem.
     */
    private final String message;
}
