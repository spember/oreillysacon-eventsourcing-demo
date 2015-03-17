package pember.demo.escommerce.services

import com.thirdchannel.eventsource.Aggregate
import com.thirdchannel.eventsource.Event
import com.thirdchannel.eventsource.EventService
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pember.demo.escommerce.entities.events.BaseEvent
import pember.demo.escommerce.repositories.EventRepository

import javax.persistence.Transient
import java.lang.reflect.Field

/**
 * @author Steve Pember
 */
@Slf4j
@Service
class BaseEventService implements EventService {

    @Autowired
    EventRepository eventRepository

    @Override
    List<Event> findAllEventsForAggregate(Aggregate aggregate) {
        eventRepository.findByAggregateIdOrderByRevisionAsc(aggregate.id)
    }

    @Override
    List<Event> findAllEventsForAggregates(List<Aggregate> aggregates) {
        eventRepository.findByAggregateIdInOrderByRevisionAsc(aggregates*.id)
    }

    @Override
    List<Event> findAllEventsForAggregateSinceRevision(Aggregate aggregate, int revision) {
        return null
    }

    @Override
    List<Event> findAllEventsForAggregateSinceDate(Aggregate aggregate, Date date) {
        return null
    }

    @Override
    List<Event> findAllEventsForAggregateInRange(Aggregate aggregate, Date begin, Date end) {
        return null
    }

    @Override
    List<Event> findAllEventsForAggregatesInRange(List<Aggregate> aggregate, Date begin, Date end) {
        return null
    }

    @Override
    boolean save(Event event) {
        eventRepository.save(event) != null
    }

    @Override
    boolean save(List<Event> events) {
        eventRepository.save(events)
        true
    }


}
