package cz.sinko.morosystems.api.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for setting correlation id to MDC and response header.
 *
 * @author Radovan Šinko
 */
@Component
@Order(1)
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String REQUEST_HEADER_NAME = "X-Correlation-ID";

    /**
     * Filter for setting correlation id to MDC and response header.
     *
     * @param request the request
     * @param response the response
     * @param filterChain the filter chain
     * @throws ServletException the servlet exception
     * @throws IOException the io exception
     */
    @Override
    protected void doFilterInternal(final @NonNull HttpServletRequest request,
                                    final @NonNull HttpServletResponse response,
                                    final @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (!SkipLoggingFilter.skip(request)) {
            String correlationId = request.getHeader(REQUEST_HEADER_NAME);

            if (correlationId == null || correlationId.isBlank()) {
                correlationId = UUID.randomUUID().toString();
            }
            try {
                MDC.put("correlation-id", correlationId);
                response.addHeader(REQUEST_HEADER_NAME, correlationId);
                filterChain.doFilter(request, response);
            } finally {
                MDC.clear();
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
