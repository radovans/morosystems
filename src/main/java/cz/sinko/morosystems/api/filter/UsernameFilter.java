package cz.sinko.morosystems.api.filter;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cz.sinko.morosystems.repository.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for setting user login to MDC.
 *
 * @author Radovan Šinko
 */
@Component
@Order()
public class UsernameFilter extends OncePerRequestFilter {

    /**
     * Filter for setting user login to MDC.
     *
     * @param request     the request
     * @param response    the response
     * @param filterChain the filter chain
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    @Override
    protected void doFilterInternal(final @NonNull HttpServletRequest request,
            final @NonNull HttpServletResponse response,
            final @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (!SkipLoggingFilter.skip(request)) {
            final String userLogin = getUserLogin();

            try {
                MDC.put("user-id", userLogin);
                filterChain.doFilter(request, response);
            } finally {
                MDC.clear();
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * Get the current authenticated user's login from Spring Security.
     *
     * @return the user's login or null if not authenticated
     */
    private String getUserLogin() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            final Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return ((User) principal).getUsername();
            }
        }
        return null; // Return null if no user is authenticated
    }
}
