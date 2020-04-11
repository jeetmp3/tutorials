package demo.multitenant.app.multischema.filters;

import demo.multitenant.app.common.context.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(TenantFilter.class);
//    @Value("${application.valid-schemas}")
    private Set<String> validSchemas = new HashSet<>(Arrays.asList("google", "facebook"));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String value = request.getHeader("x-tenant-id");
        logger.info("Tenant got from request: {}", value);
        if (value != null && validSchemas.contains(value)) {
            TenantContext.setCurrentTenant(value);
        }
        filterChain.doFilter(request, response);
        TenantContext.clear();
    }
}
