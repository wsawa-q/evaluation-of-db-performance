package cz.cuni.mff.java.kurinna.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceMysqlCayenneApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceMysqlCayenneApplication.class, args);
    }

}
