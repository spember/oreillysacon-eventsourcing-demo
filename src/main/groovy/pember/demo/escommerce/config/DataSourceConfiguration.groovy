package pember.demo.escommerce.config

import groovy.transform.CompileStatic
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy

import javax.sql.DataSource

/**
 * @author Steve Pember
 */
@CompileStatic
@Configuration
class DataSourceConfiguration {

    @Bean
    DataSource dataSource() {
        new TransactionAwareDataSourceProxy(new LazyConnectionDataSourceProxy(dataSourceUnproxied()))
    }

    @Bean
    @ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
    DataSource dataSourceUnproxied() {
        new org.apache.tomcat.jdbc.pool.DataSource()
    }
}