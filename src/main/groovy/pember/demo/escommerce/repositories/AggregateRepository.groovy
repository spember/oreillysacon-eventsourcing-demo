package pember.demo.escommerce.repositories

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pember.demo.escommerce.entities.BaseAggregate

/**
 * @author Steve Pember
 */
@Repository
interface AggregateRepository extends CrudRepository<BaseAggregate, UUID> {

    @Query("select count(a) from BaseAggregate a where a.id = ?1")
    int countById(UUID id);

    @Modifying
    @Query("update BaseAggregate b set b.revision = ?1, b.aggregateDescription = ?2 where b.id = ?3 and b.revision = ?4")
    int setRevisionAndAggregateDescription(int revision, String aggregateDescription, UUID id, int expectedRevision)
}
