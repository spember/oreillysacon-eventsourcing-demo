package pember.demo.escommerce.config

import com.thirdchannel.eventsource.EventSourceService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import pember.demo.escommerce.entities.Person
import pember.demo.escommerce.entities.Product
import pember.demo.escommerce.entities.ShoppingCart
import pember.demo.escommerce.entities.events.person.AddressUpdatedEvent
import pember.demo.escommerce.entities.events.person.CartAssignedEvent
import pember.demo.escommerce.entities.events.person.NameChangedEvent
import pember.demo.escommerce.services.ProductService
import pember.demo.escommerce.services.ShoppingCartService

import javax.transaction.Transactional

/**
 * @author Steve Pember
 */
@Slf4j
@CompileStatic
@Configuration
class FixtureConfig implements InitializingBean {

    @Autowired
    ProductService productService

    @Autowired
    ShoppingCartService shoppingCartService

    @Autowired
    EventSourceService eventSourceService

    @Transactional
    void afterPropertiesSet() {

        if (productService.count() == 0) {
            log.info("Creating products and users")

            Product product = new Product(aggregateDescription: "Coffee Maker One")
            productService.setSku(product, "TP123")
            productService.setSku(product, "TP12345")
            productService.setImageUrl(product, "maker_1.jpg")
            productService.changePrice(product, 59.99)
            productService.updateInventory(product, 100)
            productService.updateInventory(product, -1)

            productService.save(product)


            product = new Product(aggregateDescription: "Maker 2")
            productService.setSku(product, "TP12346")
            productService.setImageUrl(product, "maker_2.jpeg")
            productService.changePrice(product, 99.95)
            productService.updateInventory(product, 50)

            productService.save(product)


            product = new Product(aggregateDescription: "Maker 3")
            productService.setSku(product, "CM99999")
            productService.setImageUrl(product, "maker_3.jpg")
            productService.changePrice(product, 25.00)
            productService.updateInventory(product, 200)

            productService.save(product)


            product = new Product(aggregateDescription: "Maker 4")
            productService.setSku(product, "TP12347")
            productService.setImageUrl(product, "maker_4.jpg")
            productService.changePrice(product, 199.99)
            productService.updateInventory(product, 15)

            productService.save(product)


            Person demoPerson = new Person(aggregateDescription: "Demo Person")


            ShoppingCart cart = new ShoppingCart(aggregateDescription: "Test Cart")
            shoppingCartService.save(cart)
            //demoPerson.applyChange(new CartAssignedEvent())
            CartAssignedEvent event = new CartAssignedEvent()
            event.cartId = cart.id
            demoPerson.applyChange(event)


            demoPerson.applyChange(new NameChangedEvent(name: "Bob Smith"))
            demoPerson.applyChange(new AddressUpdatedEvent(address: "123 Demo Road, DemoTown, MA"))

            eventSourceService.save(demoPerson)



        }
    }

}
