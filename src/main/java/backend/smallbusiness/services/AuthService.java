package backend.smallbusiness.services;

import backend.smallbusiness.configuration.CorsConfiguration;
import backend.smallbusiness.filters.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class AuthService extends WebSecurityConfigurerAdapter {
    final private CorsConfiguration corsConfiguration;
    final private JwtRequestFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(corsConfiguration.corsFilter(),
                ChannelProcessingFilter.class);
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .antMatchers("/v3/api-docs",
                        "/otp/**"
                        ,"/product"
                        ,"/product/view-images/**"
                        ,"/user/**"
                ).permitAll()
                .antMatchers(HttpMethod.POST,"/invoice").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

    }
}
