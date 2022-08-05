package io.github.huafoog.fir.common.feign.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * @author DELL
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableFeignClients
public @interface EnableFirFeignClients {

    // 扫描路径
    String[] value() default {};

    // 扫描路径
    String[] basePackages() default {"io.github.huafoog.fir"};

    // 扫描类
    Class<?>[] basePackageClasses() default {};

    /**
     * 可以自定义 feign 配置类，自定义 Decoder Encoder 等组件
     * 默认的配置类是 FeignClientsConfiguration
     * @return
     */
    Class<?>[] defaultConfiguration() default {};

    // 指定 FeignClient 类，如果不为空，则路径扫描失效
    Class<?>[] clients() default {};
}

