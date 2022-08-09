package io.github.huafoog.fir.generator.config;

import io.github.huafoog.fir.generator.interceptor.DataSourceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 青杉
 * @Date: 8/8/2022 下午 5:54
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DataSourceInterceptor())
                .addPathPatterns("/**");
    }

}
