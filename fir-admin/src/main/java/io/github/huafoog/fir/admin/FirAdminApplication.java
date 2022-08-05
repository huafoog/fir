package io.github.huafoog.fir.admin;

import io.github.huafoog.fir.common.feign.annotation.EnableFirFeignClients;
import io.github.huafoog.fir.swagger.annotation.EnableFirSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author DELL
 */
@EnableDiscoveryClient
@EnableFirSwagger2
@SpringBootApplication
@EnableFirFeignClients
public class FirAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirAdminApplication.class, args);
    }

}
