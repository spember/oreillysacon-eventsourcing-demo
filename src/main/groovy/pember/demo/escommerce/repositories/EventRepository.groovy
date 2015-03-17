package pember.demo.escommerce.repositories

import org.springframework.data.repository.CrudRepository
import pember.demo.escommerce.entities.events.BaseEvent

/**
 * @author Steve Pember
 */
interface EventRepository extends CrudRepository<BaseEvent, UUID> {

    List<BaseEvent> findByAggregateIdOrderByRevisionAsc(UUID aggregateId)

    List<BaseEvent> findByAggregateIdInOrderByRevisionAsc(List<UUID> aggregateIds)
}
