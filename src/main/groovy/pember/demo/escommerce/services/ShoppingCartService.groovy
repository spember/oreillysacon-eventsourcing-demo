package pember.demo.escommerce.services

import com.thirdchannel.eventsource.Aggregate
import com.thirdchannel.eventsource.EventSourceService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pember.demo.escommerce.entities.Product
import pember.demo.escommerce.entities.ShoppingCart
import pember.demo.escommerce.entities.events.cart.CartClearedEvent
import pember.demo.escommerce.entities.events.cart.ProductAddedEvent
import pember.demo.escommerce.entities.events.cart.ProductCartEvent
import pember.demo.escommerce.entities.events.cart.ProductRemovedEvent
import pember.demo.escommerce.repositories.ShoppingCartRepository

import javax.transaction.Transactional

import static java.lang.Math.abs

/**
 * @author Steve Pember
 */
@CompileStatic
@Slf4j
@Service
class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository
    private final ProductService productService
    private final EventSourceService eventSourceService

    @Autowired ShoppingCartService(ShoppingCartRepository scr, EventSourceService ess, ProductService ps) {
        shoppingCartRepository = scr
        eventSourceService = ess
        productService = ps
    }

    ShoppingCartRepository getShoppingCartRepository() {
        shoppingCartRepository
    }


    ShoppingCart get(String uuid) {
        ShoppingCart cart = (ShoppingCart)eventSourceService.get(UUID.fromString(uuid))
        eventSourceService.loadCurrentState(cart)
        cart
    }

    boolean save(ShoppingCart cart) {
        eventSourceService.save(cart)
    }

    boolean adjustProduct(ShoppingCart cart, String productUUID, int count) {
        Product product = productService.get(productUUID)
        if (product.inventoryOnHand > 0) {
            ProductCartEvent event
            if (count >= 0) {
                log.debug("Adding ${count} ${productUUID}")
                event = new ProductAddedEvent(productId: product.id.toString(), count: count)
            } else {
                count = abs(count)
                log.debug("removing ${count} ${productUUID}")
                event = new ProductRemovedEvent(productId: product.id.toString(), count: count)
            }
            cart.applyChange(event)
            println " ---Saving cart"

            eventSourceService.save(cart)

        } else {
            false
        }
    }

    List<Map<Object,Object>> buildCartDetails(ShoppingCart cart) {
        List results = []
        if (cart.products) {
            List<Aggregate> aggregates =  eventSourceService.getAll(cart.products.keySet().toList())
            eventSourceService.loadCurrentState(aggregates)
            aggregates.each {Aggregate a->
                results << [
                        product: a,
                        count: cart.products[a.id]
                ]
            }
        }

        results
    }

    @Transactional
    boolean clearCart(ShoppingCart cart) {
        cart.applyChange(new CartClearedEvent())
        eventSourceService.save(cart)
        true
    }


}
