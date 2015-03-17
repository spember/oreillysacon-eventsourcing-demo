package pember.demo.escommerce.repositories

import org.springframework.data.repository.CrudRepository
import pember.demo.escommerce.entities.ShoppingCart

/**
 * @author Steve Pember
 */
interface ShoppingCartRepository extends CrudRepository<ShoppingCart, UUID>{
}
