package pember.demo.escommerce.entities.events.cart

import com.thirdchannel.eventsource.Aggregate
import com.thirdchannel.eventsource.annotation.EventData
import groovy.transform.CompileStatic
import groovy.transform.ToString
import pember.demo.escommerce.entities.ShoppingCart
import pember.demo.escommerce.entities.events.BaseEvent

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Transient

/**
 * @author Steve Pember
 */
@CompileStatic
@ToString
@Entity
@DiscriminatorValue("shoppingcart-cart-event")
class ProductCartEvent extends BaseEvent {

    @Transient @EventData UUID productId
    @Transient @EventData int count = 0

    @Override
    void process(Aggregate root) {
        ShoppingCart cart = (ShoppingCart)root
        if (!cart.products) { cart.products = [:] }
        if (!cart.products.containsKey(productId)) { cart.products[productId] = 0 }
    }

    void setProductId(String s) {
        productId = UUID.fromString(s)
    }
}
