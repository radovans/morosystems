package cz.sinko.morosystems.connector.common;

/**
 * Specific exception raised when call at SystemApiClient failed on 4xx.
 *
 * @author Radovan Šinko
 */
public class SystemApiClientException extends MoneyManagerException {

    /**
     * SystemApiClientException.
     *
     * @param error   error code
     * @param message error message
     */
    public SystemApiClientException(final ErrorCode error, final String message) {
        super(error, message);
    }

    /**
     * SystemApiClientException.
     *
     * @param error     error code
     * @param message   error message
     * @param throwable exception
     */
    public SystemApiClientException(final ErrorCode error, final String message, final Throwable throwable) {
        super(error, message, throwable);
    }
}
