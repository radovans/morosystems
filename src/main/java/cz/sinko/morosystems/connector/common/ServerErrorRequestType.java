package cz.sinko.morosystems.connector.common;

/**
 * Types of HTTP INTERNAL SERVER ERROR (500) error response.
 *
 * @author Radovan Šinko
 */
public enum ServerErrorRequestType implements ErrorCode {

    INVALID_REQUEST,
    SERVICE_UNAVAILABLE,
    UNAUTHORIZED;

    @Override
    public String getErrorCode() {
        return name();
    }

    @Override
    public String getErrorDesc() {
        return name();
    }
}
