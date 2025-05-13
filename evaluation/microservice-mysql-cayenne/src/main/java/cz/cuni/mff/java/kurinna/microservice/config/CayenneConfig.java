package cz.cuni.mff.java.kurinna.microservice.config;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.datasource.DataSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CayenneConfig {
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dataSourceDriverClassName;

    @Bean
    public ServerRuntime serverRuntime() {
        ServerRuntime runtime = ServerRuntime.builder()
                .dataSource(DataSourceBuilder
                        .url(dataSourceUrl)
                        .userName(dataSourceUsername)
                        .password(dataSourcePassword)
                        .driver(dataSourceDriverClassName)
                        .build())
                .addConfig("cayenne-project.xml")
                .build();

        return runtime;
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ObjectContext objectContext(ServerRuntime runtime) {
        return runtime.newContext();
    }
}