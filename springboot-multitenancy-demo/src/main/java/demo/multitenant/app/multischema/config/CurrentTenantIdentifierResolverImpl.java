package demo.multitenant.app.multischema.config;

import demo.multitenant.app.common.context.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    @Value("${application.default-schema}")
    private String defaultSchema;

    private static final Logger logger = LoggerFactory.getLogger(CurrentTenantIdentifierResolverImpl.class);

    @Override
    public String resolveCurrentTenantIdentifier() {
        String t = TenantContext.getCurrentTenant();
        if (t == null) {
            return defaultSchema;
        }
        logger.info("Current tenant resolved: {}", t);
        return t;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
