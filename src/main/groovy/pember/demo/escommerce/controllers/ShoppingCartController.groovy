package pember.demo.escommerce.controllers

import com.thirdchannel.eventsource.EventSourceService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import pember.demo.escommerce.entities.Person
import pember.demo.escommerce.entities.events.cart.ProductAddedEvent
import pember.demo.escommerce.services.PersonService
import pember.demo.escommerce.services.ProductService
import pember.demo.escommerce.services.ShoppingCartService

import javax.transaction.Transactional

/**
 * @author Steve Pember
 */
@Slf4j
@CompileStatic
@Controller
class ShoppingCartController {

    private final ShoppingCartService shoppingCartService
    private final PersonService personService
    private final EventSourceService eventSourceService

    @Autowired
    ShoppingCartController(ShoppingCartService scs, PersonService ps, EventSourceService es) {
        shoppingCartService = scs
        personService = ps
        eventSourceService = es
    }

    @RequestMapping("/cart/count")
    public @ResponseBody Map<String, Integer> getCartCount() {
        // Person should have an attached cart
        Person currentPerson = personService.getCurrentPerson()

        currentPerson.shoppingCart = shoppingCartService.get(currentPerson.shoppingCartId.toString())
        assert currentPerson.shoppingCart
        [count: currentPerson.shoppingCart.count()]
    }

    @Transactional
    @RequestMapping(name="cart-adjust", value = "/cart", method = [RequestMethod.PATCH])
    public @ResponseBody Map<String, Integer> addItemToCart(@RequestBody Map<String, Object> body) {
        String productId = body.productId
        int count = Integer.parseInt(body.count.toString())
        Person currentPerson = personService.getCurrentPerson()
        currentPerson.shoppingCart = shoppingCartService.get(currentPerson.shoppingCartId.toString())
        if (count != 0) {
            shoppingCartService.adjustProduct(currentPerson.shoppingCart, productId, count)
        }
        [count: currentPerson.shoppingCart.count()]
    }

    @RequestMapping(value="/cart", method = [RequestMethod.GET])
    public @ResponseBody List<Map<Object,Object>> listCart() {
        Person currentPerson = personService.getCurrentPerson()
        currentPerson.shoppingCart = shoppingCartService.get(currentPerson.shoppingCartId.toString())
        shoppingCartService.buildCartDetails(currentPerson.shoppingCart)
    }


    @RequestMapping(value="/cart", method=[RequestMethod.DELETE])
    public @ResponseBody Map<String, Boolean> clearCart() {
        Person currentPerson = personService.getCurrentPerson()
        currentPerson.shoppingCart = shoppingCartService.get(currentPerson.shoppingCartId.toString())
        shoppingCartService.clearCart(currentPerson.shoppingCart)
        [success: true]
    }


}
