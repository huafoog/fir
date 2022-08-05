package io.github.huafoog.fir.gateway;

import io.github.huafoog.fir.security.annotation.EnableFirResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFirResource
public class FirGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirGatewayApplication.class, args);
    }

}
