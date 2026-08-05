package com.StudentLibrary.Studentlibrary.config;

import com.StudentLibrary.Studentlibrary.Security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.StudentLibrary.Studentlibrary.Security.JwtFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        auth.authenticationProvider(authenticationProvider());

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .antMatchers("/register", "/authenticate").permitAll()
//                .antMatchers("/payment/**").permitAll()
//                .anyRequest().authenticated()
//                .antMatchers("/register", "/login", "/authenticate").permitAll()

                .authorizeRequests()

                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .antMatchers(
                        "/register",
                        "/authenticate",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/uploads/profile-pictures/**"
                ).permitAll()

                .antMatchers(
                        "/dashboard/admin",
                        "/createBook",
                        "/updateBook",
                        "/deleteBook",
                        "/createAuthor",
                        "/createStudent",
                        "/payment/all",

                        "/google-books/**"
                ).hasRole("ADMIN")

                .antMatchers(
                        "/issueBook",
                        "/returnBook",
                        "/payment/create",
                        "/payment/start",
                        "/payment/verify",
                        "/payment/my",

                        "/payment/order"
                )
                .hasAnyRole("ADMIN","STUDENT")
                .antMatchers(
                        "/student/profile",
                        "/student/profile/picture"
                )
                .hasRole("STUDENT")

                .antMatchers("/getBooksByPage")
                .authenticated()

                .anyRequest()
                .permitAll();

        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
       // configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}