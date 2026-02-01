package com.mybatis.mybatisstudy.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author：marco.pan
 * @ClassName：Datasource
 * @Description：
 * @date: 2025年08月16日 20:18
 */
@Configuration
public class BeanConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDefaultAutoCommit(false); // 明确关闭
        return ds;
    }
}
