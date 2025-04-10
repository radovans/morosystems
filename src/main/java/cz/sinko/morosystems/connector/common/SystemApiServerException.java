package cz.sinko.morosystems.connector.common;

/**
 * Specific exception raised when call at SystemApiClient failed on 5xx.
 *
 * @author Radovan Šinko
 */
public class SystemApiServerException extends MoneyManagerException {

    /**
     * SystemApiServerException.
     *
     * @param error   error code
     * @param message error message
     */
    public SystemApiServerException(final ErrorCode error, final String message) {
        super(error, message);
    }
}
