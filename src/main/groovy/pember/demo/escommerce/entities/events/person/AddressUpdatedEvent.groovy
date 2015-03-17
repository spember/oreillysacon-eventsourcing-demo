package pember.demo.escommerce.entities.events.person

import com.thirdchannel.eventsource.Aggregate
import com.thirdchannel.eventsource.annotation.EventData
import groovy.transform.CompileStatic
import groovy.transform.ToString
import pember.demo.escommerce.entities.Person
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
@DiscriminatorValue("person-address-updated")
class AddressUpdatedEvent extends BaseEvent {

    @Transient
    @EventData
    String address

    @Override
    void process(Aggregate root) {
        ((Person)root).address = address
    }
}
