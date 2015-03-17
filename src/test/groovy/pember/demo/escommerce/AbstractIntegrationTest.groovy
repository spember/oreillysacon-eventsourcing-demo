package pember.demo.escommerce

import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * @author Steve Pember
 */
@ActiveProfiles('test')
@ContextConfiguration(loader=SpringApplicationContextLoader, classes=Application)
@WebIntegrationTest(randomPort=true)
@Transactional
abstract class AbstractIntegrationTest extends Specification {
}
