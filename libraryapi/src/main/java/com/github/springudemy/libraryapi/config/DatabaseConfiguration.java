package com.github.springudemy.libraryapi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    // @Bean
    // public DataSource dataSource(){
        
    //     DriverManagerDataSource ds = new DriverManagerDataSource();

    //     ds.setUrl(url);
    //     ds.setUsername(username);
    //     ds.setPassword(password);
    //     ds.setDriverClassName(driver);

    //     return ds;

    // }

    @Bean
    DataSource hikariDataSource(){

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); // Maximo de conexões
        config.setMinimumIdle(1); // Tamanho inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); // 600 mil ms (10 minutos)
        config.setConnectionTimeout(100000); // Timeout para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); // Query de Teste
        
        return new HikariDataSource(config);

    }
}
