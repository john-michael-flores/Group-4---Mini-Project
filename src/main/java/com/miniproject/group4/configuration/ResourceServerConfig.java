package com.miniproject.group4.configuration;

import com.miniproject.group4.enums.UserRoles;
import com.miniproject.group4.handler.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter  {
    private static final String RESOURCE_ID = "resource_id";
    private static final String PAYROLL_END_POINT = "/payrolls/**";
    private static final String USER_END_POINT = "/users/**";
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    /**
     * Configuring the HttpSecurity.
     * <br>
     * Disabling anonymous access and defining authorized request
     * <br>
     * using mvcMatchers and throwing a handler if the user requesting
     * <br>
     * access does not have permission.
     * <br>
     * Disabling security access to h2-console.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                anonymous()
                .disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, PAYROLL_END_POINT, USER_END_POINT).hasAnyRole(UserRoles.ADMIN.toString(), UserRoles.USER.toString())
                .mvcMatchers(PAYROLL_END_POINT, USER_END_POINT).hasRole(UserRoles.ADMIN.toString())
                .and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
        http.headers().frameOptions().disable();
    }
}
