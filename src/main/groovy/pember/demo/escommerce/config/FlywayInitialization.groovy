package pember.demo.escommerce.config

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.flywaydb.core.Flyway
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

import javax.sql.DataSource

/**
 * @author Steve Pember
 */
@CompileStatic
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Profile(['dev', 'production'])
@Slf4j
class FlywayInitialization implements InitializingBean {

   @Autowired private DataSource dataSource

    @Override
    void afterPropertiesSet() throws Exception {
        log.info("Getting ready to migrate")
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        int count = flyway.migrate();
        log.info("Processed {} migrations", count)
    }
}
