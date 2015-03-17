package pember.demo.escommerce.repositories

import org.springframework.data.repository.CrudRepository
import pember.demo.escommerce.entities.BaseAggregate
import pember.demo.escommerce.entities.Product

/**
 * @author Steve Pember
 */
interface ProductRepository extends CrudRepository<Product, UUID> {
}
