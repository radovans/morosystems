package cz.sinko.morosystems.connector.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles common exceptions.
 *
 * @author Radovan Šinko
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse illegalArgumentException(final IllegalArgumentException e) {
        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }
        return new ErrorResponse(BadRequestType.INVALID_REQUEST.getErrorCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(SystemApiClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse systemApiClientException(final SystemApiClientException e) {
        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }
        return new ErrorResponse(e.getError().getErrorCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(SystemApiServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse systemApiServerException(final SystemApiServerException e) {
        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }
        return new ErrorResponse(e.getError().getErrorCode(), e.getMessage());
    }
}
