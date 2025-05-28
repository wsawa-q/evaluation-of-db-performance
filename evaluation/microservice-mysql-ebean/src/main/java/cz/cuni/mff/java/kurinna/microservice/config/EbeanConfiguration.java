package cz.cuni.mff.java.kurinna.microservice.config;

import cz.cuni.mff.java.kurinna.microservice.service.CurrentUser;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class EbeanConfiguration {

    @Bean
    @Lazy
    public Database database(CurrentUser currentUser, DataSource dataSource) {
        DatabaseConfig config = new DatabaseConfig();
        config.setDataSource(dataSource);
        config.setName("db");
        config.setCurrentUserProvider(currentUser);

        config.loadFromProperties();

        return DatabaseFactory.create(config);
    }
}