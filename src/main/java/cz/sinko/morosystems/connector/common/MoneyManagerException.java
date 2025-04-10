package cz.sinko.morosystems.connector.common;

import org.springframework.util.Assert;

import lombok.Getter;

/**
 * Service Proxy parent exception. Use this exception when you want to define specific exceptional state.
 *
 * @author Radovan Šinko
 */
@Getter
public abstract class MoneyManagerException extends RuntimeException {

    private final ErrorCode error;

    /**
     * One-arg constructor.
     *
     * @param error   the error code
     * @param message exception message
     */
    protected MoneyManagerException(final ErrorCode error, final String message) {
        super(message);

        Assert.notNull(error, "error must not be null");
        this.error = error;
    }

    /**
     * Two-arg constructor.
     *
     * @param error     the error code
     * @param message   exception message
     * @param throwable exception itself
     */
    protected MoneyManagerException(final ErrorCode error, final String message, final Throwable throwable) {
        super(message, throwable);

        Assert.notNull(error, "error must not be null");
        this.error = error;
    }
}
