package pember.demo.escommerce.controllers

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import pember.demo.escommerce.entities.Product
import pember.demo.escommerce.services.ProductService

/**
 * @author Steve Pember
 */
@Slf4j
@CompileStatic
@Controller
class ProductController {


    @Autowired
    ProductService productService

    @RequestMapping("/product")
    public @ResponseBody List<Product> list() {
        log.info("--- looking up all products ---")
        List<Product> products = productService.list()
        products
    }

    @RequestMapping("/product/{productId}")
    public @ResponseBody List<Product> getProduct(@PathVariable(value="productId") String productId) {
        log.info("--- looking up product: ${productId} ---")

        Product product = productService.get(productId)
        [product]
    }
}


