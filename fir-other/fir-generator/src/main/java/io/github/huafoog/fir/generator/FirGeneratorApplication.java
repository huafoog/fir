package io.github.huafoog.fir.generator;

import io.github.huafoog.fir.swagger.annotation.EnableFirSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 代码生成器
 * @author 青杉
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFirSwagger2
public class FirGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirGeneratorApplication.class, args);
    }
}
