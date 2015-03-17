package pember.demo.escommerce.controllers

import com.thirdchannel.eventsource.EventSourceService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.hibernate.event.spi.EventSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import pember.demo.escommerce.entities.Person
import pember.demo.escommerce.entities.Product
import pember.demo.escommerce.repositories.PersonRepository
import pember.demo.escommerce.services.PersonService
import pember.demo.escommerce.services.ProductService
import pember.demo.escommerce.services.ReportService

/**
 * @author Steve Pember
 */
@Slf4j
@CompileStatic
@Controller
class MainController {

    @Autowired ReportService reportService
    @Autowired ProductService productService
    @Autowired PersonService personService

    @RequestMapping("/")
    public String index() {
        return "index"
    }


    @RequestMapping("/reports")
    public String reports(Model model) {
        Map results = reportService.findProducts()
        println "Report is ${results.toString()}"
        List<Product> products = results.collect {k, v-> productService.get(k.toString())}
        model.addAttribute("report", results)
        model.addAttribute("products", products)
        model.addAttribute("user", personService.getCurrentPerson())
        return "reports"
    }

}
