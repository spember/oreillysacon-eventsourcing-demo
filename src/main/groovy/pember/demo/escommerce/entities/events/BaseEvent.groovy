package pember.demo.escommerce.entities.events

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.thirdchannel.eventsource.AbstractEvent
import com.thirdchannel.eventsource.Aggregate
import com.thirdchannel.eventsource.Event
import groovy.transform.CompileStatic
import org.hibernate.annotations.Type
import org.springframework.beans.factory.annotation.Autowired

import javax.persistence.DiscriminatorColumn
import javax.persistence.DiscriminatorType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.Table
import javax.persistence.Transient
import java.lang.reflect.Field

import static javax.persistence.InheritanceType.SINGLE_TABLE

/**
 * @author Steve Pember
 */
@Entity
@CompileStatic
@Table(name="event")
@Inheritance(strategy=SINGLE_TABLE)
@DiscriminatorValue("base")
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
class BaseEvent implements Event {

    @Id
    @Type(type="pg-uuid")
    UUID id = UUID.randomUUID()
    int revision
    @Type(type="pg-uuid")
    UUID aggregateId
    Date date = new Date()
    String data
    String userId

    @Transient
    String clazz

    @Override
    void restoreData(Map data) {
        data.each {k, v ->
            this.setProperty(k.toString(), v)
        }
    }

    @Override
    void process(Aggregate root) {

    }
}
