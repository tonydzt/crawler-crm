package com.kasuo.crawler.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConf {

    @Bean(name = "dataSource")
//    @Qualifier("dataSource")
//    @Primary
    @ConfigurationProperties(prefix = "druid")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

//    @Bean(name = "dataSourceBt")
//    @ConfigurationProperties(prefix = "druidbt")
//    public DataSource druidDataSourceBt() {
//        return new DruidDataSource();
//    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

//    @Bean(name = "jdbcTemplateBt")
//    public JdbcTemplate jdbcTemplateBt(@Qualifier("dataSourceBt") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
}
