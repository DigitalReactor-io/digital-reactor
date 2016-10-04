package io.digitalreactor.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * Created by MStepachev on 07.09.2016.
 */
@Configuration
public class MongoDbConfig {

    @Value("${mongodb.host}")
    private String host;

    public @Bean
    MongoClient mongo() throws UnknownHostException {
        return new MongoClient(host);
    }
}
