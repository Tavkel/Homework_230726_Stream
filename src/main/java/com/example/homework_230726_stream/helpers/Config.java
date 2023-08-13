package com.example.homework_230726_stream.helpers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class Config {
    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://89.179.242.75:5432/SkyproLearning")
                .driverClassName("org.postgresql.Driver")
                .username(Credentials.getUser())
                .password(Credentials.getPassword())
                .build();
    }
}