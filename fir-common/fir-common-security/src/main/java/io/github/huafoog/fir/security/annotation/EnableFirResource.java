package io.github.huafoog.fir.security.annotation;

import io.github.huafoog.fir.security.resource.ResourceServerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

import java.lang.annotation.*;

/**
 * 开启资源
 * // 注解需要使用@EnableWebFluxSecurity而非@EnableWebSecurity,因为SpringCloud Gateway基于WebFlux
 * @author DELL
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({ResourceServerConfig.class})
@EnableWebFluxSecurity
public @interface EnableFirResource {
}
