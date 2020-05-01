package com.hxiloj.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	

	 @Autowired
	    private ClientDetailsService clientDetailsService;

	    @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

	    @Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	    	auth
	        .ldapAuthentication()
	          .userDnPatterns("uid={0},ou=people")
	          .groupSearchBase("ou=groups")
	          .contextSource()
	            .url("ldap://localhost:8389/dc=springframework,dc=org")
	            .and()
	          .passwordCompare()
	            .passwordEncoder(new BCryptPasswordEncoder())
	            .passwordAttribute("userPassword");
	    

		}

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	                .csrf().disable()
	                .cors().disable()
	                .authorizeRequests()
	                .antMatchers("/oauth/**").permitAll()
	                .anyRequest().authenticated()
	                .and().httpBasic()
	                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    }

	    @Bean
	    @Autowired
	    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
	        var handler = new TokenStoreUserApprovalHandler();
	        handler.setTokenStore(tokenStore);
	        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
	        handler.setClientDetailsService(clientDetailsService);
	        return handler;
	    }

	    @Bean
	    @Autowired
	    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
	        var store = new TokenApprovalStore();
	        store.setTokenStore(tokenStore);
	        return store;
	    }

	    @Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
	
}
