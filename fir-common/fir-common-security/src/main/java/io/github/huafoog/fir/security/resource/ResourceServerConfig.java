package io.github.huafoog.fir.security.resource;

import io.github.huafoog.fir.common.core.constant.AuthConstants;
import io.github.huafoog.fir.security.annotation.EnableFirResource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 资源服务器配置
 */
@ComponentScan("io.github.huafoog.fir.security")
@ConditionalOnClass(EnableFirResource.class)
public class ResourceServerConfig {
    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private CustomServerAccessDeniedHandler customServerAccessDeniedHandler;
    @Autowired
    private CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint;
    // 白名单配置
//    private WhiteListConfig whiteListConfig;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        // 自定义处理JWT请求头过期或签名错误的结果
        http.oauth2ResourceServer().authenticationEntryPoint(customServerAuthenticationEntryPoint);
        http.authorizeExchange()
//                .pathMatchers(ArrayUtil.toArray(whiteListConfig.getUrls(),String.class))
//                .permitAll()
                .anyExchange().access(authorizationManager)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customServerAccessDeniedHandler) // 处理未授权
                .authenticationEntryPoint(customServerAuthenticationEntryPoint) //处理未认证
                .and().csrf().disable();

        return http.build();
    }

    /**
     * @linkhttps://blog.csdn.net/qq_24230139/article/details/105091273
     * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
     * 需要把jwt的Claim中的authorities加入
     * 方案：重新定义ReactiveAuthenticationManager权限管理器，默认转换器JwtGrantedAuthoritiesConverter
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstants.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstants.AUTHORITY_CLAIM_NAME);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
