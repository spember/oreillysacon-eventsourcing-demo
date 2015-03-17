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
@DiscriminatorValue("shoppingcart-order-placed")
class OrderPlacedEvent extends BaseEvent {

    // in production, this particular event might have have a side-effect wherein it emits an event to the inventory or
    // billing system(s)

    @Override
    void process(Aggregate root) {
        ((ShoppingCart)root).products = [:]
    }
}
