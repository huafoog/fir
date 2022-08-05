package io.github.huafoog.fir.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Security 安全配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 安全拦截机制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 放行
                .antMatchers("/auth/**","/v2/api-docs")
                .permitAll()
                // 其他请求必须认证通过
                .anyRequest().authenticated()
                .and()
//                .formLogin() // 允许表单登录
//                .successForwardUrl("/login-success") //自定义登录成功跳转页
                .csrf().disable();
    }

    /**
     * token持久化配置
     */
    @Bean
    public TokenStore tokenStore() {
        // 本地内存存储令牌
        return new InMemoryTokenStore();
    }

    /**
     * 	 认证管理器配置
     *
     *   不定义没有 password grant_type,密码模式需要AuthenticationManager支持
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() {
        return authentication -> daoAuthenticationProvider().authenticate(authentication);
    }

    /**
     * 认证是由 AuthenticationManager 来管理的，
     * 但是真正进行认证的是 AuthenticationManager 中定义的 AuthenticationProvider，
     * 用于调用userDetailsService进行验证
     */
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

//    /**
//     * 用户详情服务
//     */
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        // 测试方便采用内存存取方式
//        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//        userDetailsService.createUser(User.withUsername("user_1").password(passwordEncoder().encode("123456")).authorities("ROLE_USER").build());
//        userDetailsService.createUser(User.withUsername("user_2").password(passwordEncoder().encode("1234567")).authorities("ROLE_USER").build());
//        return userDetailsService;
//    }


    /**
     * 设置授权码模式的授权码如何存取，暂时采用内存方式
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    /**
     * jwt token解析器
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 对称密钥，资源服务器使用该密钥来验证
        converter.setSigningKey("JoNyCw");
        return converter;
    }


    /**
     * 注册一个认证管理器对象到容器
     *  如果不配置SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 密码编码对象
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder()  {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
