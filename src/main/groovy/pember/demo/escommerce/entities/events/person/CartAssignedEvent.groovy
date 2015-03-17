package pember.demo.escommerce.entities.events.person

import com.thirdchannel.eventsource.Aggregate
import com.thirdchannel.eventsource.annotation.EventData
import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.springframework.beans.factory.annotation.Autowired
import pember.demo.escommerce.entities.Person
import pember.demo.escommerce.entities.events.BaseEvent
import pember.demo.escommerce.repositories.ShoppingCartRepository

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Transient

/**
 * @author Steve Pember
 */
@CompileStatic
@ToString
@Entity
@DiscriminatorValue("person-cart-assigned")
class CartAssignedEvent extends BaseEvent {

    @Transient
    @EventData
    UUID cartId

    void setCartId(String id) {
        cartId = UUID.fromString(id)
    }


    @Override
    void process(Aggregate root) {

        ((Person)root).shoppingCartId = cartId

    }
}
