package io.github.huafoog.fir.auth.config;

import io.github.huafoog.fir.auth.component.AuthTokenExceptionHandler;
import io.github.huafoog.fir.security.model.FirUser;
import io.github.huafoog.fir.common.core.constant.AuthConstants;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务配置
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final ClientDetailsService clientDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final AuthorizationCodeServices authorizationCodeServices;
    private final AuthTokenExceptionHandler authTokenExceptionHandler;

    // jwtToken解析器
    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 客户端详情配置，
     * 比如client_id，secret
     * 当前这个服务就如同QQ平台，拉勾网作为客户端需要qq平台进行登录授权认证等，提前需要到QQ平台注册，QQ平台会给拉勾网
     * 颁发client_id等必要参数，表明客户端是谁
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        clients.inMemory()
                .withClient("1")
                .secret("123456")
                .scopes("all", "test")
                .resourceIds("admin")
                // autoApprove 可跳过授权页直接返回code
                .autoApprove("all")
                .redirectUris("http://www.baidu.com")
                //客户端认证所支持的授权类型 1:客户端凭证 2:账号密码 3:授权码 4:token刷新 5:简易模式
//                .authorizedGrantTypes(CLIENT_CREDENTIALS, PASSWORD, REFRESH_TOKEN, AUTHORIZATION_CODE, IMPLICIT)
                .authorizedGrantTypes("password", "refresh_token")
                //用户角色
                .authorities("admin")
                //允许自动授权
                .autoApprove(false)
                //token 过期时间
                .accessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(12))
                //refresh_token 过期时间
                .refreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));
//        clients.inMemory()// 客户端信息存储在什么地方，可以在内存中，可以在数据库里
//                .withClient("client_lagou")  // 添加一个client配置,指定其client_id
//                .secret("123456")                   // 指定客户端的密码/安全码
//                .resourceIds("autodeliver")         // 指定客户端所能访问资源id清单，此处的资源id是需要在具体的资源服务器上也配置一样
//                // 认证类型/令牌颁发模式，可以配置多个在这里，但是不一定都用，具体使用哪种方式颁发token，需要客户端调用的时候传递参数指定
//                .authorizedGrantTypes("password", "refresh_token")
//                // 客户端的权限范围，此处配置为all全部即可
//                .scopes("all");
//        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 认证服务器是玩转token的，那么这里配置token令牌管理相关（token此时就是一个字符串，当下的token需要在服务器端存储，
     * 那么存储在哪里呢？都是在这里配置）
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        // 配置授权服务器端点的属性
        endpoints.authenticationManager(authenticationManager)    //认证管理器
                .tokenStore(tokenStore)
                .authorizationCodeServices(authorizationCodeServices)
                .userDetailsService(userDetailsService)
                .exceptionTranslator(authTokenExceptionHandler);

//        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
//        tokenEnhancers.add(tokenEnhancer());
//        tokenEnhancers.add(jwtAccessTokenConverter());
//        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
//
//        endpoints
//                .authenticationManager(authenticationManager) // 指定认证管理器，随后注入一个到当前类使用即可
//                .accessTokenConverter(jwtAccessTokenConverter())
//                .tokenEnhancer(tokenEnhancerChain)
//                .userDetailsService(userDefaultDetailsService)
//                // refresh_token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
//                //      1.重复使用：access_token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
//                //      2.非重复使用：access_token过期刷新时， refresh_token过期时间延续，在refresh_token有效期内刷新而无需失效再次登录
//                .reuseRefreshTokens(false);
    }

    /**
     * 配置令牌端点安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                //设置密码编辑器
                .passwordEncoder(passwordEncoder)
                //开启 /oauth/token_key 的访问权限控制
                .tokenKeyAccess("permitAll()")
                //开启 /oauth/check_token 验证端口认证权限访问
                .checkTokenAccess("permitAll()")
                // 允许表单认证
//                .allowFormAuthenticationForClients()
        ;
    }

    /**
     * 令牌服务配置
     *
     * @return 令牌服务对象
     */
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        // 令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        // 令牌默认有效期2小时
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 2);
        // 刷新令牌默认有效期3天
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);
        return tokenServices;
    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
                new ClassPathResource("huafoog.jks"), "huafoog123456".toCharArray());
        KeyPair keyPair = factory.getKeyPair(
                "huafoog", "huafoog123456".toCharArray());
        return keyPair;
    }

    /**
     * JWT内容增强
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> map = new HashMap<>(2);
            FirUser user = (FirUser) authentication.getUserAuthentication().getPrincipal();
            map.put(AuthConstants.JWT_USER_ID_KEY, user.getId());
            map.put(AuthConstants.JWT_CLIENT_ID_KEY, user.getClientId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
            return accessToken;
        };
    }
}

