package io.digitalreactor.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * Created by MStepachev on 07.09.2016.
 */
@Configuration
public class MongoDbConfig {
    public @Bean
    MongoClient mongo() throws UnknownHostException {
        return new MongoClient("localhost");
    }
}
