package io.digitalreactor.core;

import io.digitalreactor.core.service.TemporalTokenStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by MStepachev on 09.09.2016.
 */
@Configuration
public class ServiceConfig {

    @Bean
    public TemporalTokenStorage temporalTokenStorage() {
        return new TemporalTokenStorage();
    }
}
