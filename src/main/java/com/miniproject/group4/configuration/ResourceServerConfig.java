package com.miniproject.group4.configuration;

import com.miniproject.group4.enums.UserRoles;
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
    private static final String PAYROLL_END_POINT = "/payrolls/**";
    private static final String USER_END_POINT = "/users/**";
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
                .mvcMatchers(HttpMethod.GET, PAYROLL_END_POINT, USER_END_POINT).hasAnyRole(UserRoles.ROLE_ADMIN.toString(), UserRoles.ROLE_USER.toString())
                .mvcMatchers(PAYROLL_END_POINT, USER_END_POINT).hasRole(UserRoles.ROLE_ADMIN.toString())
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        http.headers().frameOptions().disable();
    }
}
