package cz.sinko.morosystems.api.filter;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Skips logging for certain requests.
 *
 * @author Radovan Šinko
 */
public final class SkipLoggingFilter {

    /**
     * Do not instantiate.
     */
    private SkipLoggingFilter() {
        // Prevent instantiation
    }

    /**
     * Checks if the request should be skipped from logging.
     *
     * @param request the request
     * @return true if the request should be skipped, false otherwise
     */
    public static boolean skip(final HttpServletRequest request) {
        final String requestUrl = request.getRequestURI();
        return requestUrl != null && (requestUrl.contains("/actuator")
                || requestUrl.contains("/v3/api-docs")
                || requestUrl.contains("/swagger-ui.html")
                || requestUrl.contains("/swagger-resources")
                || requestUrl.contains("/csrf")
                || requestUrl.contains("/webjars/springfox-swagger-ui")
                || (requestUrl.endsWith("/") && requestUrl.length() == 1));
    }
}
