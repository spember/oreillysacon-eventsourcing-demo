package pember.demo.escommerce.repositories

import org.springframework.data.repository.CrudRepository
import pember.demo.escommerce.entities.Person

/**
 * @author Steve Pember
 */
interface PersonRepository extends CrudRepository<Person, UUID>{
}
