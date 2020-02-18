package tutorials.logging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tutorials.logging.impl.ConsoleLogger;

/**
 * Auto-Configuration class.
 */
@Configuration
public class LoggingAutoConfiguration {

    // We can define beans here

    @ConditionalOnMissingBean(Logger.class)
    @Bean
    public Logger getLogger() {
        return new ConsoleLogger();
    }
}
