package pember.demo.escommerce.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thirdchannel.eventsource.AbstractAggregate
import com.thirdchannel.eventsource.AbstractFunctionalAggregate
import com.thirdchannel.eventsource.Aggregate
import com.thirdchannel.eventsource.Event
import groovy.transform.CompileStatic
import org.hibernate.annotations.Type

import javax.persistence.DiscriminatorColumn
import javax.persistence.DiscriminatorType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.Table
import javax.persistence.Transient

import static javax.persistence.InheritanceType.SINGLE_TABLE;

/**
 * @author Steve Pember
 */
@Entity
@Table(name="aggregate")
@Inheritance(strategy=SINGLE_TABLE)
@DiscriminatorValue("base")
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
class BaseAggregate extends AbstractFunctionalAggregate {

    @Id
    @Type(type="pg-uuid")
    UUID id = UUID.randomUUID()
    String aggregateDescription


    @JsonIgnore
    int revision = 0

    @JsonIgnore
    @Transient
    List<Event> uncommittedEvents = []


    void setUncommittedEvents(List<Event> events) {
        uncommittedEvents = events
    }
}
