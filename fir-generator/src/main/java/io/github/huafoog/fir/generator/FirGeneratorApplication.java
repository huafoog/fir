package io.github.huafoog.fir.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FirGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirGeneratorApplication.class, args);
    }

}
