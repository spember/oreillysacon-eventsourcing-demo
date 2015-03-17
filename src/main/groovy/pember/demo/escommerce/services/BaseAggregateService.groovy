package pember.demo.escommerce.services

import com.thirdchannel.eventsource.Aggregate
import com.thirdchannel.eventsource.AggregateService
import com.thirdchannel.eventsource.Event
import com.thirdchannel.eventsource.EventService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pember.demo.escommerce.repositories.AggregateRepository

import javax.transaction.Transactional

/**
 * @author Steve Pember
 */
@Slf4j
@Service
class BaseAggregateService implements AggregateService {


    private BaseEventService baseEventService


    private AggregateRepository aggregateRepository

    @Autowired BaseAggregateService(BaseEventService bse, AggregateRepository ar) {
        aggregateRepository = ar
        baseEventService = bse
    }


    @Override
    Aggregate get(UUID id) {
        aggregateRepository.findOne(id)
    }

    @Override
    List<Aggregate> getAll(List<UUID> ids) {
        aggregateRepository.findAll(ids)
    }

    @Override
    Aggregate getOrCreate(UUID id, String aggregateDescription) {
        return null
    }

    @Override
    Aggregate create(String description) {
        return null
    }

    @Override
    boolean exists(UUID aggregateId) {
        println "--- Calling count ="
        boolean result = (aggregateRepository.exists(aggregateId))
        println "---- done count"
        result
    }

    @Override
    int getCurrentRevision(UUID id) {
        Aggregate aggregate = aggregateRepository.findOne(id)
        if (aggregate) {
            return aggregate.revision
        } else {
            -1
        }
    }

    @Override
    int update(Aggregate aggregate, int expectedRevision) {
        aggregateRepository.setRevisionAndAggregateDescription(aggregate.revision, aggregate.aggregateDescription, aggregate.id, expectedRevision)
        1
    }

    @Override
    int save(Aggregate aggregate) {
        aggregateRepository.save(aggregate) == null ? 0 : 1
    }
}
