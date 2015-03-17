package pember.demo.escommerce

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationContext
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * @author Steve Pember
 */
@Slf4j
@CompileStatic
@EnableTransactionManagement
@SpringBootApplication(exclude=DataSourceAutoConfiguration)
class Application {

    static void main(String[] args) {
        ApplicationContext ctx = new SpringApplicationBuilder()
                .showBanner(false)
                .sources(Application)
                .build().run(args)
        log.info("Ready to go!")
    }
}
