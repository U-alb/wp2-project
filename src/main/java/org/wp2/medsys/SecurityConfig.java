package org.wp2.medsys;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean                       //this puts it in Spring’s container
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();   // strength = 10 by default
    }
}
