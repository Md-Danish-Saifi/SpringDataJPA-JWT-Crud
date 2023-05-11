package com.example.myProject.config;

import com.example.myProject.Security.AuthenticationFilter;
import com.example.myProject.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    /*-------------------------------------------------------------------------------------------------------*/
//	By default security of spring boot

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
//		auth.inMemoryAuthentication().withUser("root").password("root").roles("USER");
//		
//	}

    /* Security for all api
     * @Override protected void configure(HttpSecurity http) throws Exception {
     *
     * http.csrf().disable();
     * http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();
     * }
     */


    /* URL based security
     * @Override protected void configure(HttpSecurity http) throws Exception {
     *
     * http.csrf().disable();
     * http.authorizeRequests().antMatchers("/myProject/**").fullyAuthenticated().
     * and().httpBasic(); }
     */

//	Role based security
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http.csrf().disable();
//		http.authorizeRequests().antMatchers("/myProject/**").hasRole("ADMIN").anyRequest().fullyAuthenticated().and().httpBasic();	
//	}

//	@Bean
//	public static NoOpPasswordEncoder passwordEncoder() {
//		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//	}


    /*--------------------------------------------------------------------------------------------------------------------*/
//	JWT AUTHENTICATION (JSON WEB TOKEN)

    private static final String[] AUTH_WHITELIST = {

            // -- swagger ui
            "/swagger-resources/**",
            "/h2/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/api/v1/download/**",
            "/swagger.json",
            "/v2/api-docs/**",
            
//            Endpoint
            "/hello",
            
    };

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationFilter jwtFilter;


//	@Bean
//    public AuthenticationFilter authenticationJwtTokenFilter() {
//
//        return new AuthenticationFilter();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();

//		http
//        .headers()
//        .contentSecurityPolicy("default-src 'self'");

        http.headers()
                .httpStrictTransportSecurity()
                .includeSubDomains(true)
                .maxAgeInSeconds(31536000);

        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/generatetoken").permitAll()
                .antMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(
                jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
