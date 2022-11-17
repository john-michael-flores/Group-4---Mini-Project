package com.miniproject.group4.configuration;

import com.miniproject.group4.enums.UserTypes;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter  {
    private static final String RESOURCE_ID = "resource_id";
    private static final String CONTROLLER_END_POINT = "/payrolls/**";
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                anonymous()
                .disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, CONTROLLER_END_POINT).hasAnyRole(UserTypes.ADMIN.toString(), UserTypes.USER.toString())
                .mvcMatchers(CONTROLLER_END_POINT).hasRole(UserTypes.ADMIN.toString())
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        http.headers().frameOptions().disable();
    }
}
