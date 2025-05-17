package cz.cuni.mff.java.kurinna.microservice.config;

import cz.cuni.mff.java.kurinna.microservice.service.CurrentUser;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EbeanConfiguration {

    @Bean
    public Database database(CurrentUser currentUser) {
        DatabaseConfig config = new DatabaseConfig();
        config.setCurrentUserProvider(currentUser);

        config.loadFromProperties();

        return DatabaseFactory.create(config);
    }
}
