package pember.demo.escommerce.entities

import com.thirdchannel.eventsource.EventSourceService
import org.springframework.beans.factory.annotation.Autowired
import pember.demo.escommerce.AbstractIntegrationTest
import pember.demo.escommerce.entities.events.person.PersonOrderPlacedEvent
import pember.demo.escommerce.entities.events.person.AddressUpdatedEvent
import pember.demo.escommerce.entities.events.person.NameChangedEvent

/**
 * @author Steve Pember
 */
class PersonEventsIntegrationSpec extends AbstractIntegrationTest {

    @Autowired private EventSourceService eventSourceService

    def setup() {

    }

    void "Each Person event should update the Person appropriately"() {
        given:
        Person person = new Person()
        //eventSourceService.save(person)
        UUID personId = person.id

        when:
            person.applyChange(new NameChangedEvent(name: "Bob Smith"))
            person.applyChange(new AddressUpdatedEvent(address: "123 Test Street"))
            person.applyChange(new NameChangedEvent(name: "Robert Test Smith"))
            person.applyChange(new PersonOrderPlacedEvent(amountInCents: 10000))
            person.applyChange(new PersonOrderPlacedEvent(amountInCents: 52525))
            person.applyChange(new PersonOrderPlacedEvent(amountInCents: 9999))
            eventSourceService.save(person)

        then:
            person.name == "Robert Test Smith"
            person.address == "123 Test Street"
            person.ordersPlaced == 3
            person.totalSpentInCents == 10000 + 52525 + 9999


    }
}
