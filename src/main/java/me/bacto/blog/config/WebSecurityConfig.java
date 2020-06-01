package me.bacto.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.bacto.blog.account.domain.AccountRole;
import me.bacto.blog.auth.BaseSecurityHandler;
import me.bacto.blog.auth.ajax.AjaxAuthenticationProvider;
import me.bacto.blog.auth.ajax.filter.AjaxAuthenticationFilter;
import me.bacto.blog.auth.jwt.JwtAuthenticationProvider;
import me.bacto.blog.auth.jwt.filter.JwtAuthenticationFilter;
import me.bacto.blog.auth.jwt.matcher.SkipRequestMatcher;
import me.bacto.blog.auth.jwt.matcher.MatchConditions;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENTRY_POINT = "/login";
    private static final String TOKEN_ENTRY_POINT = "/token";
    private static final String ERROR_ENTRY_POINT = "/error";
    private static final String POST_ENTRY_POINT = "/post/**";
    private static final String USER_ENTRY_POINT = "/user/**";
    private static final String ROOT_ENTRY_POINT = "/**";
    private static final String[] SWAGGER_ENTRY_POINTS = {"/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/api-docs/**"};

    private final ObjectMapper objectMapper;
    private final BaseSecurityHandler securityHandler;
    private final JwtAuthenticationProvider jwtProvider;
    private final AjaxAuthenticationProvider ajaxProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxProvider)
                .authenticationProvider(jwtProvider);
    }

    @Bean
    public AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
        AjaxAuthenticationFilter filter = new AjaxAuthenticationFilter(ajaxAuthenticationMatcher(), objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(securityHandler);
        filter.setAuthenticationFailureHandler(securityHandler);
        return filter;
    }

    @Bean
    public AntPathRequestMatcher ajaxAuthenticationMatcher() {
        return new AntPathRequestMatcher(LOGIN_ENTRY_POINT, HttpMethod.POST.name());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtAuthenticationMatcher());
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationFailureHandler(securityHandler);
        return filter;
    }

    @Bean
    public SkipRequestMatcher jwtAuthenticationMatcher() {
        MatchConditions matchConditions = new MatchConditions();
        matchConditions.add(POST_ENTRY_POINT, HttpMethod.GET);
        matchConditions.add(LOGIN_ENTRY_POINT);
        matchConditions.add(TOKEN_ENTRY_POINT);
        matchConditions.add(ERROR_ENTRY_POINT);
        return new SkipRequestMatcher(matchConditions);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(SWAGGER_ENTRY_POINTS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), FilterSecurityInterceptor.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .accessDecisionManager(accessDecisionManager())
                .antMatchers(TOKEN_ENTRY_POINT).permitAll()
                .antMatchers(LOGIN_ENTRY_POINT).permitAll()
                .antMatchers(ERROR_ENTRY_POINT).permitAll()
                .antMatchers(ROOT_ENTRY_POINT).permitAll()
                .antMatchers(HttpMethod.GET, POST_ENTRY_POINT).permitAll()
                    .antMatchers(POST_ENTRY_POINT).hasRole(AccountRole.USER.getRoleValue())
                .antMatchers(USER_ENTRY_POINT).hasRole(AccountRole.USER.getRoleValue())

                .anyRequest().permitAll();

    }

    private AccessDecisionManager accessDecisionManager() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(AccountRole.ADMIN.getRoleValue() + " > " + AccountRole.USER.getRoleValue());

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);
        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);

        return new AffirmativeBased(voters);
    }
}
