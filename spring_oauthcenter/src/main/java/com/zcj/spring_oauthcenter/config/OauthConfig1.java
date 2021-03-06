package com.zcj.spring_oauthcenter.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zcj.spring_oauthcenter.po.TbUser;
import com.zcj.spring_oauthcenter.service.impl.UserDeatilServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class OauthConfig1 extends AuthorizationServerConfigurerAdapter {

    /**
     * 核心认证管理器(为了使用密码模式，不然可以不用)
     */
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private DruidDataSource druidDataSource;

    @Autowired
    private UserDeatilServiceimpl userDeatilServiceimpl;

    /**
     * 令牌储存模式
     *
     * @return
     */
    @Bean
    TokenStore tokenStore() {
        return new JdbcTokenStore(druidDataSource);
    }


    /**
     * 密码验证格式
     *
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    DefaultTokenServices defaultTokenServices() {
//
//    }

    @Bean
    public ClientDetailsService jdbcClientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(druidDataSource);
        return jdbcClientDetailsService;
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(druidDataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        允许form提交到oauth/tocken和/oauth/authorize
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("permitAll()");
//        super.configure(security);
    }

    /**
     * 客户端授权模式
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory().withClient("client")
//                .secret(passwordEncoder().encode("secret"))
//                .authorizedGrantTypes("authorization_code").scopes("app").redirectUris("http://127.0.0.1:8088/test");
        String dev = passwordEncoder().encode("dev");
        System.out.println(dev);
        //这里和上面内存模式不一样，数据库模式是吧上面的secret，clientid都放在了数据库表中，所以你要手动在oauth_client_details中配置
        clients.withClientDetails(jdbcClientDetailsService());
    }


    /**
     * 配置令牌的储存方式
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDeatilServiceimpl)
                .tokenStore(tokenStore()).authorizationCodeServices(authorizationCodeServices());
        // 配置tokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 60 * 4); //tocken失效时间 30天
        endpoints.tokenServices(tokenServices);


    }

    /**
     * Jwt资源令牌转换器
     *
     * @return accessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        return new JwtAccessTokenConverter() {

            /**
             * 重写增强token的方法
             *
             * @param accessToken
             *            资源令牌
             * @param authentication
             *            认证
             * @return 增强的OAuth2AccessToken对象
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                Object principal = authentication.getUserAuthentication().getPrincipal();
                if (principal instanceof TbUser) {
                    TbUser loginAppUser = (TbUser) principal;
                    Map<String, Object> infoMap = new HashMap<>();
                    infoMap.put("username", loginAppUser.getUsername());
                    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(infoMap);
                }

                return super.enhance(accessToken, authentication);
            }
        };
    }

}
