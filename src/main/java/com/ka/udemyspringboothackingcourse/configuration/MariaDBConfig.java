package com.ka.udemyspringboothackingcourse.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("com.ka.udemycourse.mariadb")
@Data
public class MariaDBConfig {
    private String jdbcUrl;
    private String username;
    private String password;
}
