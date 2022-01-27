package com.security;

import com.client.views.admin.EmployeeView;
import com.client.views.admin.FlightView;
import com.client.views.admin.PersonalDataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_SUCCESS_URL = "/home";
    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Vaadin handles CSRF internally
        http.csrf().disable()

                // Register our CustomRequestCache, which saves unauthorized access attempts, so the user is redirected after login.
                .requestCache().requestCache(new CustomRequestCache())

                // Restrict access to our application.
                .and().authorizeRequests()

                // Allow all Vaadin internal requests.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                // Allow all requests by logged-in users.
               // .antMatchers("/**").permitAll().anyRequest().authenticated()

                .antMatchers("/login").permitAll()
                .antMatchers("/flightForUserRestApi","/flightForUserRestApi/**", "/reservationRestApi","/reservationRestApi/**").hasAuthority("PASSENGER")
                .antMatchers("/home").hasAnyAuthority("PASSENGER","ADMIN")
                .antMatchers("/employeesRestApi","employeesRestApi/**","/flightRestApi","/flightRestApi/**","/personalDataRestApi","/personalDataRestApi/**","/planesRestApi","/planesRestApi/**").hasAuthority("ADMIN")
               // .anyRequest().hasAuthority("ADMIN")

                // Configure the login page.
                .and().formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .successHandler(loginSuccessHandler)
//                .defaultSuccessUrl(LOGIN_SUCCESS_URL)
//                .successForwardUrl(LOGIN_SUCCESS_URL)
                .failureUrl(LOGIN_FAILURE_URL)

                // Configure logout
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                .password("{noop}userpass")
//                .roles("USER")
//                .build();

        return new CustomUserDetailsService();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Allows access to static resources, bypassing Spring Security.
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                // Client-side JS
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",

                // icons and images
                "/icons/**",
                "/images/**",
                "/styles/**",

                // (development mode) H2 debugging console
                "/h2-console/**");
    }


    public static boolean isAccessGranted(Class<?> securedClass) {
        final boolean publicView =
                PersonalDataView.class.equals(securedClass)
                        // || LoginView.class.equals(securedClass)
                        || FlightView.class.equals(securedClass)
                        || EmployeeView.class.equals(securedClass);

        // Always allow access to public views
        if (publicView) {
            return true;
        }
        return false;
    }
}