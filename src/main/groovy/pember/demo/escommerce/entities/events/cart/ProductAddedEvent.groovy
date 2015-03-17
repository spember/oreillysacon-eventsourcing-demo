package pember.demo.escommerce.entities.events.cart

import com.thirdchannel.eventsource.Aggregate
import pember.demo.escommerce.entities.ShoppingCart
import groovy.transform.CompileStatic
import groovy.transform.ToString
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

/**
 * @author Steve Pember
 */
@CompileStatic
@ToString
@Entity
@DiscriminatorValue("shoppingcart-product-added")
class ProductAddedEvent extends ProductCartEvent {

    @Override
    void process(Aggregate root) {
        super.process(root)
        ((ShoppingCart)root).products[productId] += count
    }

}
