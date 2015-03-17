package pember.demo.escommerce.controllers

import com.thirdchannel.eventsource.EventSourceService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.hibernate.event.spi.EventSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import pember.demo.escommerce.entities.Person
import pember.demo.escommerce.entities.Product
import pember.demo.escommerce.repositories.PersonRepository
import pember.demo.escommerce.services.ProductService

/**
 * @author Steve Pember
 */
@Slf4j
@CompileStatic
@Controller
class MainController {

    @RequestMapping("/")
    public String index() {

        return "index"
    }


}
