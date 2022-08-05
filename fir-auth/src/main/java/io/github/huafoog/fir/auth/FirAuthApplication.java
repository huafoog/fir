package io.github.huafoog.fir.auth;

import io.github.huafoog.fir.common.feign.annotation.EnableFirFeignClients;
import io.github.huafoog.fir.swagger.annotation.EnableFirSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author DELL
 */
@EnableFirSwagger2
@EnableDiscoveryClient
@SpringBootApplication
@EnableFirFeignClients
public class FirAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirAuthApplication.class, args);
    }

}
