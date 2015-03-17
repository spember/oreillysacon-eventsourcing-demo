package pember.demo.escommerce.entities.events.cart

import com.thirdchannel.eventsource.Aggregate
import groovy.transform.CompileStatic
import groovy.transform.ToString
import pember.demo.escommerce.entities.ShoppingCart
import pember.demo.escommerce.entities.events.BaseEvent

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

/**
 * @author Steve Pember
 */
@CompileStatic
@ToString
@Entity
@DiscriminatorValue("shoppingcart-cleared")
class CartClearedEvent extends BaseEvent {
    // don't always need to have a saved value, events can simply perform an action
    @Override
    void process(Aggregate root) {
        ((ShoppingCart)root).products = [:]
    }
}
